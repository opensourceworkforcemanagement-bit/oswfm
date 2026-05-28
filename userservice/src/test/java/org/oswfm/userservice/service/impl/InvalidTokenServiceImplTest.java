package org.oswfm.userservice.service.impl;

import org.oswfm.userservice.base.AbstractBaseServiceTest;
import org.oswfm.userservice.exception.TokenAlreadyInvalidatedException;
import org.oswfm.commons.model.user.entity.InvalidTokenEntity;
import org.oswfm.userservice.repository.InvalidTokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InvalidTokenServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private InvalidTokenServiceImpl invalidTokenService;

    @Mock
    private InvalidTokenRepository invalidTokenRepository;

    @Test
    void givenTokenIds_whenInvalidateTokens_thenSaveAllTokens() {

        // Given
        LocalDateTime expiry1 = LocalDateTime.now().plusHours(1);
        LocalDateTime expiry2 = LocalDateTime.now().plusHours(2);
        Map<String, LocalDateTime> tokenExpiryMap = Map.of(
                "token1", expiry1,
                "token2", expiry2
        );

        // When
        invalidTokenService.invalidateTokens(tokenExpiryMap);

        // Then
        ArgumentCaptor<List<InvalidTokenEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(invalidTokenRepository).saveAll(captor.capture());
        List<InvalidTokenEntity> capturedTokens = captor.getValue();

        assertThat(capturedTokens)
                .hasSize(2)
                .extracting("tokenId")
                .containsExactlyInAnyOrder("token1", "token2");

    }

    @Test
    void givenInvalidToken_whenCheckForInvalidityOfToken_thenThrowTokenAlreadyInvalidatedException() {

        // Given
        String tokenId = "invalidToken";
        InvalidTokenEntity invalidTokenEntity = InvalidTokenEntity.builder().tokenId(tokenId).build();

        // When
        when(invalidTokenRepository.findByTokenId(tokenId)).thenReturn(Optional.of(invalidTokenEntity));

        // Then
        assertThatThrownBy(() -> invalidTokenService.checkForInvalidityOfToken(tokenId))
                .isInstanceOf(TokenAlreadyInvalidatedException.class)
                .hasMessageContaining(tokenId);

        // Verify
        verify(invalidTokenRepository).findByTokenId(tokenId);

    }

    @Test
    void givenValidToken_whenCheckForInvalidityOfToken_thenDoNotThrowException() {

        // Given
        String tokenId = "validToken";

        // When
        when(invalidTokenRepository.findByTokenId(tokenId)).thenReturn(Optional.empty());

        // Then
        invalidTokenService.checkForInvalidityOfToken(tokenId);

        // Verify
        verify(invalidTokenRepository).findByTokenId(tokenId);

    }

}
