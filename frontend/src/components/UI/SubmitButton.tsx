"use client";

import React, { Fragment } from "react";
import { useFormStatus } from "react-dom";

type SubmitButtonProps = {
	buttonStyle?: string;
	type?: string;
};

const SubmitButton: React.FC<SubmitButtonProps> = ({
	buttonStyle,
}) => {
	const { pending } = useFormStatus();

	return (
		<Fragment>
			<button
				type="submit"
				aria-disabled={pending}
				disabled={pending}
				className={`base-btn ${buttonStyle}`.trim()}
			>
				{pending ? "Submitting" : "Submit"}
			</button>
		</Fragment>
	);
};

export default SubmitButton;