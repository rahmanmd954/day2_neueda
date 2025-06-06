import {useState, useTransition} from "react";
import Credits from "../credits/Credits";

const AboutPage = () => {

    const [showCredits, setShowCredits] = useState(false);
    const [isPending, startTransition] = useTransition();

    const handleClick = () => {
        //setShowCredits(!showCredits);
        startTransition(() => {
            setShowCredits((prev) => !prev);
        });
    }

    return (
        <div>
        <h1>About Page</h1>
        <p>This is the payments app</p>
            <button onClick={handleClick}>{showCredits ? "hide" : "show"} credits</button>
            {showCredits && <Credits />}
        </div>
    );
}

export default AboutPage;
