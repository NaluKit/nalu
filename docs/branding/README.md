# Nalu branding

A surfing penguin and the rounded lowercase `nalu` wordmark, using the same animal mascot
approach and palette as Paniolo, Pololei and the GWT Spring Boot archetype. The turquoise
surfboard and wave connect the mascot with Nalu. Existing artwork under `etc/LogoNalu`
is preserved.

## Files

| File | Use |
| --- | --- |
| `nalu-banner.svg` / `.png` | Horizontal logo for light backgrounds |
| `nalu-banner-dark.svg` / `.png` | Horizontal logo for dark backgrounds |
| `nalu-logo.svg` / `.png` | Stacked logo for light backgrounds |
| `nalu-logo-dark.svg` / `.png` | Stacked logo for dark backgrounds |
| `nalu-icon.svg` / `.png` | Penguin and surfboard, square canvas, light backgrounds |
| `nalu-icon-dark.svg` / `.png` | Penguin and surfboard, square canvas, dark backgrounds |
| `nalu-mark.svg` / `.png` | Small n-and-wave mark for light backgrounds |
| `nalu-mark-dark.svg` / `.png` | Small mark for dark backgrounds |
| `nalu-favicon.svg` | Small mark on a rounded navy tile |
| `nalu-favicon-32.png` | Browser favicon, 32 × 32 px |
| `nalu-favicon-180.png` | Apple touch icon, 180 × 180 px |
| `nalu-favicon-512.png` | App icon or avatar, 512 × 512 px |
| `favicon.ico` | Browser favicon containing 16, 32 and 48 px images |

## Colors and formats

- Navy: `#01244F`
- Turquoise: `#019ABC`
- Pale blue for dark backgrounds: `#E2ECF5`

All SVGs contain real vector paths, with no embedded bitmaps, fonts, gradients, filters
or external resources. The cleaned penguin, surfboard and wave are traced to smooth
Bezier paths; the belly and face openings remain transparent. The n, a and l outlines
come from the existing Paniolo wordmark; the u is a rotated n from the same lettering.
The outlined wordmark needs no installed font.

Banner, logo, icon and mark backgrounds are transparent. Dark variants replace navy with
pale blue and retain turquoise. Favicons have a rounded navy tile with transparent corners.
Use the small mark at 16–32 px and the complete penguin at larger sizes.

PNGs are rendered from their matching SVGs: banner 900 × 439 px, stacked logo 640 × 792 px,
icon 256 × 256 px and mark 512 × 512 px. PNG color channels use exactly the brand colors;
edge smoothing is represented by partial alpha.

## README usage

```html
<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/branding/nalu-banner-dark.svg">
    <img src="docs/branding/nalu-banner.svg" alt="Nalu" width="480">
  </picture>
</p>
```

## Favicons

When serving the files from the same directory as the page:

```html
<link rel="icon" href="nalu-favicon.svg" type="image/svg+xml">
<link rel="icon" href="favicon.ico" sizes="16x16 32x32 48x48">
<link rel="apple-touch-icon" href="nalu-favicon-180.png">
```

## Use of the logo

This logo identifies the Nalu project. As with the Paniolo branding, the logo is not offered
under the code's Apache License 2.0. Use it unchanged to refer to Nalu; do not use it to
identify other products or imply endorsement by NaluKit or Frank Hossfeld.
