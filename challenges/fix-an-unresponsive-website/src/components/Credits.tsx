import React, { useState } from 'react';

export const Credits = () => {
    const [credits, setCredits] = useState<string | null>(null);

    const handleShowCredits = () => {
        const worker = new Worker(new URL('./creditsWorker.ts', import.meta.url));
        worker.onmessage = (event) => {
            setCredits(event.data);
            worker.terminate();
        };
        worker.postMessage('start');
    };

    return (
        <div>
            <button onClick={handleShowCredits}>Show Credits</button>
            {credits && <div>{credits}</div>}
        </div>
    );
};