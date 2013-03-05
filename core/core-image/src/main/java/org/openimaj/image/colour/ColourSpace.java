/**
 * Copyright (c) 2011, The University of Southampton and the individual contributors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openimaj.image.colour;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;

/**
 * Different colour space types with conversion methods.
 *
 * @author Jonathon Hare (jsh2@ecs.soton.ac.uk)
 */
public enum ColourSpace {
	/**
	 * RGB colour space
	 */
	RGB {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return input;
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return input;
		}
	},
	/**
	 * HSV colour space
	 */
	HSV {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HSV(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.HSV_TO_RGB(input);
		}
	},
	/**
	 * HSI colour space
	 */
	HSI {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HSI(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}
	},
	/**
	 * H2SV colour space
	 *
	 * @see Transforms#RGB_TO_H2SV
	 */
	H2SV {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_H2SV(input);
		}

		@Override
		public int getNumBands() {
			return 4;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.HSV_TO_RGB(Transforms.H2SV_TO_HSV_Simple(input));
		}
	},
	/**
	 * H2SV_2 colour space
	 *
	 * @see Transforms#RGB_TO_H2SV_2
	 */
	H2SV_2 {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_H2SV_2(input);
		}

		@Override
		public int getNumBands() {
			return 4;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.HSV_TO_RGB(Transforms.H2SV2_TO_HSV_Simple(input));
		}
	},
	/**
	 * H2S colour space
	 *
	 * @see Transforms#RGB_TO_H2S
	 */
	H2S {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_H2S(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}
	},
	/**
	 * H2S_2 colour space
	 *
	 * @see Transforms#RGB_TO_H2S_2
	 */
	H2S_2 {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_H2S_2(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}
	},
	/**
	 * LUMINANCE colour space from averaging RGB
	 */
	LUMINANCE_AVG {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return new MBFImage(this, Transforms.calculateIntensity(input));
		}

		@Override
		public int getNumBands() {
			return 1;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0).clone(), input.bands.get(0).clone(), input.bands.get(0).clone());
		}
	},
	/**
	 * LUMINANCE colour space using NTSC perceptual weightings
	 */
	LUMINANCE_NTSC {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return new MBFImage(this, Transforms.calculateIntensityNTSC(input));
		}

		@Override
		public int getNumBands() {
			return 1;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0).clone(), input.bands.get(0).clone(), input.bands.get(0).clone());
		}
	},
	/**
	 * Hue colour space
	 */
	HUE {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return new MBFImage(this, Transforms.calculateHue(input));
		}

		@Override
		public int getNumBands() {
			return 1;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0).clone(), input.bands.get(0).clone(), input.bands.get(0).clone());
		}
	},
	/**
	 * Saturation colour space
	 */
	SATURATION {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return new MBFImage(this, Transforms.calculateSaturation(input));
		}

		@Override
		public int getNumBands() {
			return 1;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0).clone(), input.bands.get(0).clone(), input.bands.get(0).clone());
		}
	},
	/**
	 * Intensity normalised RGB colour space using normalisation
	 */
	RGB_INTENSITY_NORMALISED {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_RGB_NORMALISED(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return input;
		}
	},
	/**
	 * A custom (unknown) colour space
	 */
	CUSTOM {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			throw new UnsupportedOperationException("Cannot convert to the custom color-space");
		}

		@Override
		public int getNumBands() {
			return 1;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}
	},
	/**
	 * RGB with alpha colour space
	 */
	RGBA {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0), input.bands.get(1), input.bands.get(2), new FImage(
					input.bands.get(0).width, input.bands.get(0).height).addInplace(1.0f));
		}

		@Override
		public int getNumBands() {
			return 4;
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return new MBFImage(input.bands.get(0).clone(), input.bands.get(1).clone(), input.bands.get(2).clone());
		}
	},
	/**
	 * HSL colour space
	 */
	HSL {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HSL(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * HSY colour space
	 */
	HSY {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HSY(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * HS colour space
	 */
	HS {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HS(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 2;
		}
	},
	/**
	 * HS_2 colour space
	 */
	HS_2 {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_HS_2(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 2;
		}
	},
	/**
	 * H1H2 colour space (two component hue)
	 *
	 * @see Transforms#H_TO_H1H2
	 */
	H1H2 {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.H_TO_H1H2(Transforms.calculateHue(input));
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 2;
		}
	},
	/**
	 * H1H2_2 colour space (two component hue)
	 *
	 * @see Transforms#H_TO_H1H2_2
	 */
	H1H2_2 {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.H_TO_H1H2_2(Transforms.calculateHue(input));
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			throw new UnsupportedOperationException("colour transform not implemented");
		}

		@Override
		public int getNumBands() {
			return 2;
		}
	},
	/**
	 * CIE_XYZ color space, using the same transform as in OpenCV, which in turn
	 * came from:
	 * http://www.cica.indiana.edu/cica/faq/color_spaces/color.spaces.html
	 */
	CIE_XYZ {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_CIEXYZ(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.CIEXYZ_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * CIE_Lab color space, using the same transform as in OpenCV, which in turn
	 * came from: <a href=
	 * "http://www.cica.indiana.edu/cica/faq/color_spaces/color.spaces.html">
	 * http://www.cica.indiana.edu/cica/faq/color_spaces/color.spaces.html</a>
	 * <p>
	 * The resultant L values are in the range 0-100, and the a & b values are
	 * in -127..127 inclusive.
	 */
	CIE_Lab {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_CIELab(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.CIELab_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * Normalised CIE_Lab color space, using the same transform as in OpenCV,
	 * which in turn came from: <a href=
	 * "http://www.cica.indiana.edu/cica/faq/color_spaces/color.spaces.html">
	 * http://www.cica.indiana.edu/cica/faq/color_spaces/color.spaces.html</a>
	 * <p>
	 * The L, a & b values are normalised to 0..1.
	 */
	CIE_Lab_Norm {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_CIELabNormalised(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.CIELabNormalised_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * CIE L*u*v* color space (CIE 1976).
	 * <p>
	 * The resultant L values are in the range 0-100, and the u & v values are
	 * in -100..100 inclusive.
	 */
	CIE_Luv {

		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_CIELUV(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.CIELUV_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * YUV
	 * <p>
	 * The resultant Y is in the range [0, 1]; U is [-0.436, 0.436] and V is
	 * [-0.615, 0.615].
	 */
	YUV {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_YUV(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.YUV_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	},
	/**
	 * Normalised YUV.
	 * <p>
	 * Each of the Y, U and V values are in [0, 1].
	 *
	 */
	YUV_Norm {
		@Override
		public MBFImage convertFromRGB(final MBFImage input) {
			return Transforms.RGB_TO_YUVNormalised(input);
		}

		@Override
		public MBFImage convertToRGB(final MBFImage input) {
			return Transforms.YUVNormalised_TO_RGB(input);
		}

		@Override
		public int getNumBands() {
			return 3;
		}
	};
	/**
	 * Convert the given RGB image to the current colour space
	 *
	 * @param input
	 *            RGB image
	 * @return image in the current colour space
	 */
	public abstract MBFImage convertFromRGB(MBFImage input);

	/**
	 * Convert the image in this color space to RGB
	 *
	 * @param input
	 *            image in this colour space
	 * @return RGB image
	 */
	public abstract MBFImage convertToRGB(MBFImage input);

	/**
	 * Convert the image to this colour space
	 *
	 * @param input
	 *            an image
	 * @return image in this colour space
	 */
	public MBFImage convert(final MBFImage input) {
		return this.convertFromRGB(input.getColourSpace().convertToRGB(input));
	}

	/**
	 * Convert the image to the given colour space
	 *
	 * @param image
	 *            the image
	 * @param cs
	 *            the target colour space
	 * @return the converted image
	 */
	public static MBFImage convert(final MBFImage image, final ColourSpace cs) {
		return cs.convertFromRGB(image.colourSpace.convertToRGB(image));
	}

	/**
	 * Get the number of bands required by this colour space
	 *
	 * @return the number of bands
	 */
	public abstract int getNumBands();


	/**
	 * 	Sanitise the given colour array to fit the colour space format.
	 * 	It uses a number of heuristics that are as follows:
	 *
	 * 	 - if the colour has the same or more bands than the colour
	 * 	   space, then the colour is returned unchanged.
	 *   - if the colour has just one band, then it is duplicated
	 *     by the same number of bands as required by the colour space
	 *   - otherwise, the colour is duplicated and padded with 1s.
	 *
	 *  Example:
	 *  	RGBA colour space, RGB colour [1.0, 0.2, 0.4]
	 *  the result will be padded with 1s:
	 *  	[1.0, 0.2, 0.4, 1]
	 *
	 *  Example:
	 *  	HSV colour space, single band colour [0.3]
	 *  the result will be duplicated:
	 *  	[0.3, 0.3, 0.3]
	 *
	 *	@param colour The colour to sanitise
	 *	@return The sanitised colour
	 */
	public Float[] sanitise( final Float[] colour )
	{
		// If the colour is longer than the required number
		// of bands, then we'll return as is. We needn't
		// truncate as the extra bands will be ignored by
		// any renderers.
		if( colour.length >= this.getNumBands() )
			return colour;

		// If the colour is a singleton, we'll duplicate it up
		// to the correct number of bands.
		if( colour.length == 1 )
		{
			final Float[] newColour = new Float[ this.getNumBands() ];
			for( int i = 0; i < newColour.length; i++ )
				newColour[i] = colour[0];
			return newColour;
		}

		// If it's neither of the above, then we copy the current colour
		// into the new return colour, and pad with 1s.
		final Float[] newColour = new Float[ this.getNumBands() ];

		// Copy the current colour
		for( int i = 0; i < colour.length; i++ )
			newColour[i] = colour[i];

		// Pad with 1s
		for( int i = colour.length; i < newColour.length; i++ )
			newColour[i] = 1f;

		return newColour;
	}
}
