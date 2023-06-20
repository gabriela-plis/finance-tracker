import zxcvbn from 'zxcvbn';

export default function calculatePasswordStrength(password: string) {
    const result = zxcvbn(password);
    const score = result.score;
    return score;
}