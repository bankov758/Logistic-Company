import React, {useState} from "react";

type Option = {
    value: string;
    placeholder: string;
};

const RadioButton: React.FC<{ options: Option[] }> = ({
  options,
}) => {
    const [selectedOption, setSelectedOption] = useState<string | null>(null);

    const handleOptionChange = (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        setSelectedOption(event.target.value);
    };

    return (
        <div>
            {options.map((option) => (
                <div key={option.value}>
                    <label key={option.value}>
                        <input
                            type="radio"
                            name="radioOptions"
                            value={option.value}
                            checked={selectedOption === option.value}
                            onChange={handleOptionChange}
                        />
                        {option.placeholder}
                    </label>
                </div>
            ))}
        </div>
    );
};

export default RadioButton;