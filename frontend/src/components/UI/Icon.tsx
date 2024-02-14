"use client";

import React from "react";
import Image from "next/image";
import { StaticImport } from "next/dist/shared/lib/get-img-props";

export type iconProps = {
    parentClassName?: string | [] | {},
    className?: string,
    src: string | StaticImport,
    width?: number,
    height?: number,
    alt: string,
    href?: string,
    target?: string,
}

const Icon: React.FC<iconProps> = (
    {
        parentClassName,
        className,
        src,
        width,
        height,
        alt,
        href,
        target
    }
) => {
    return (
        <div className={`relative ${parentClassName}`}>
            
            {href && target && <a href={href} target={target} rel='noreferrer' className='absolute inset-0' />}
            <Image
                className={className || ''}
                src={src}
                alt={alt}
                width={width}
                height={height}
                priority
            />
        </div>
    );
};

export default Icon;