import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';

export default function Results() {
  const router = useRouter();
  const [result, setResult] = useState(null);

  useEffect(() => {
    // This assumes your backend has an API that stores results temporarily
    const fetchResults = async () => {
      try {
        const response = await fetch('/api/get-latest-result'); // Adjust if needed
        const data = await response.json();
        setResult(data);
      } catch (error) {
        console.error('Failed to load results:', error);
      }
    };

    fetchResults();
  }, []);

  if (!result) {
    return <p style={{ padding: '2rem' }}>Loading results...</p>;
  }

  return (
    <div style={{ padding: '2rem', fontFamily: 'Arial' }}>
      <h1>📊 Your Results</h1>

      <p><strong>Score:</strong> {result.score} / 100</p>
      <p><strong>AI Feedback:</strong></p>
      <pre
        style={{
          backgroundColor: '#f4f4f4',
          padding: '1rem',
          borderRadius: '5px',
          whiteSpace: 'pre-wrap',
        }}
      >
        {result.feedback}
      </pre>

      <br />
      <button onClick={() => router.push('/')} style={{ marginTop: '1rem' }}>
        ⬅️ Back to Upload Page
      </button>
    </div>
  );
}