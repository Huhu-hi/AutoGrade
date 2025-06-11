import { useState } from 'react';

export default function Home() {
  const [fileContent, setFileContent] = useState('');
  const [feedback, setFeedback] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFileChange = async (event) => {
    const file = event.target.files[0];
    if (!file) return;
    const text = await file.text();
    setFileContent(text);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!fileContent) {
      alert('Please select a file first!');
      return;
    }
    setLoading(true);
    setFeedback(null);
    try {
      const response = await fetch('/grade', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: fileContent }),
      });
      if (!response.ok) throw new Error('Failed to get grading from backend');
      const data = await response.json();
      setFeedback(data);
    } catch (error) {
      alert(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        padding: '2rem',
        fontFamily: 'Arial, sans-serif',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        textAlign: 'center',
        backgroundColor: '#ffe6f0',
        color: 'black',
        minHeight: '100vh',
      }}
    >
      <h1 style={{ fontSize: '2.5rem', marginBottom: '0.5rem' }}>Welcome to AutoGrade</h1>
      <h2 style={{ fontSize: '1.5rem', marginBottom: '1.5rem' }}>
        Get instant scores powered by AI.
      </h2>

      <p style={{ fontSize: '1rem', color: '#555', maxWidth: '600px', marginBottom: '2rem' }}>
        Upload the assignment in plain text format (.txt) and receive the grade. No signup or login required.
      </p>

      <form onSubmit={handleSubmit} style={{ width: '100%', maxWidth: '400px' }}>
        <input
          type="file"
          accept=".txt"
          onChange={handleFileChange}
          style={{
            marginBottom: '1rem',
            padding: '0.5rem',
            borderRadius: '5px',
            border: '1px solid #ccc',
            backgroundColor: 'white',
            width: '100%',
          }}
        />

        <button
          type="submit"
          disabled={loading}
          style={{
            padding: '0.5rem 1rem',
            backgroundColor: '#ff66a3',
            border: 'none',
            borderRadius: '5px',
            color: 'white',
            cursor: loading ? 'not-allowed' : 'pointer',
            width: '100%',
            fontSize: '1rem',
            marginBottom: '0.5rem',
          }}
        >
          {loading ? 'Grading...' : 'Upload & Grade'}
        </button>
      </form>

      {/* Instructions */}
      <div style={{ marginTop: '2rem', maxWidth: '600px', textAlign: 'left' }}>
        <h3 style={{ fontSize: '1.2rem', marginBottom: '0.5rem' }}>How It Works</h3>
        <ul style={{ color: '#444', paddingLeft: '1.2rem', listStyle: 'disc' }}>
          <li>Upload a <strong>.txt</strong> file of the assignment.</li>
          <li>The AI reads and analyzes the writing instantly.</li>
          <li>You receive constructive feedback and a grade suggestion for the assignment.</li>
        </ul>
      </div>

      {/* Feedback Result */}
      {feedback && (
        <div
          style={{
            marginTop: '2rem',
            padding: '1rem',
            backgroundColor: '#fff',
            borderRadius: '8px',
            width: '100%',
            maxWidth: '600px',
            textAlign: 'left',
            boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
          }}
        >
          <h2 style={{ color: '#333' }}>AI Feedback & Score</h2>
          <pre style={{ whiteSpace: 'pre-wrap', fontFamily: 'monospace' }}>
            {JSON.stringify(feedback, null, 2)}
          </pre>
        </div>
      )}

      {/* Footer */}
      <footer style={{ marginTop: '4rem', color: '#999', fontSize: '0.9rem' }}>
        &copy; 2025 AutoGrade by Ballerina Hackacina. All rights reserved.
      </footer>
    </div>
  ); }