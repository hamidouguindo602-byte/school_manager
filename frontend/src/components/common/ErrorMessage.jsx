export default function ErrorMessage({ message }) { return message ? <div className="error-box" role="alert">{message}</div> : null; }
