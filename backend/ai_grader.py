import openai
import os
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

# Get your OpenAI API key from the environment
openai.api_key = os.getenv("OPENAI_KEY")

def grade_text(text):
    """
    Takes an essay or assignment as input,
    sends it to GPT-4, and returns the feedback and score.
    """
    response = openai.ChatCompletion.create(
        model="gpt-4",
        messages=[
            {
                "role": "system",
                "content": "You are a teacher grading this assignment. Give helpful feedback and a score out of 10."
            },
            {
                "role": "user",
                "content": text
            }
        ]
    )
    return response.choices[0].message.content


openai.api_key = os.getenv("OPENAI_KEY")  # Make sure you put your OpenAI key in a .env file

def grade_text(text):
    response = openai.ChatCompletion.create(
        model="gpt-4",
        messages=[
            {"role": "system", "content": "You are a teacher grading this assignment. Give feedback and a score out of 10."},
            {"role": "user", "content": text}
        ]
    )
    return response.choices[0].message.content
