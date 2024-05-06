import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './groenobject.reducer';

export const GroenobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const groenobjectEntity = useAppSelector(state => state.groenobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="groenobjectDetailsHeading">Groenobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{groenobjectEntity.id}</dd>
          <dt>
            <span id="aantalobstakels">Aantalobstakels</span>
          </dt>
          <dd>{groenobjectEntity.aantalobstakels}</dd>
          <dt>
            <span id="aantalzijden">Aantalzijden</span>
          </dt>
          <dd>{groenobjectEntity.aantalzijden}</dd>
          <dt>
            <span id="afvoeren">Afvoeren</span>
          </dt>
          <dd>{groenobjectEntity.afvoeren ? 'true' : 'false'}</dd>
          <dt>
            <span id="bereikbaarheid">Bereikbaarheid</span>
          </dt>
          <dd>{groenobjectEntity.bereikbaarheid}</dd>
          <dt>
            <span id="bergendvermogen">Bergendvermogen</span>
          </dt>
          <dd>{groenobjectEntity.bergendvermogen}</dd>
          <dt>
            <span id="bewerkingspercentage">Bewerkingspercentage</span>
          </dt>
          <dd>{groenobjectEntity.bewerkingspercentage}</dd>
          <dt>
            <span id="bgtfysiekvoorkomen">Bgtfysiekvoorkomen</span>
          </dt>
          <dd>{groenobjectEntity.bgtfysiekvoorkomen}</dd>
          <dt>
            <span id="bollen">Bollen</span>
          </dt>
          <dd>{groenobjectEntity.bollen ? 'true' : 'false'}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{groenobjectEntity.breedte}</dd>
          <dt>
            <span id="breedteklassehaag">Breedteklassehaag</span>
          </dt>
          <dd>{groenobjectEntity.breedteklassehaag}</dd>
          <dt>
            <span id="bvc">Bvc</span>
          </dt>
          <dd>{groenobjectEntity.bvc ? 'true' : 'false'}</dd>
          <dt>
            <span id="cultuurhistorischwaardevol">Cultuurhistorischwaardevol</span>
          </dt>
          <dd>{groenobjectEntity.cultuurhistorischwaardevol}</dd>
          <dt>
            <span id="draagkrachtig">Draagkrachtig</span>
          </dt>
          <dd>{groenobjectEntity.draagkrachtig ? 'true' : 'false'}</dd>
          <dt>
            <span id="ecologischbeheer">Ecologischbeheer</span>
          </dt>
          <dd>{groenobjectEntity.ecologischbeheer ? 'true' : 'false'}</dd>
          <dt>
            <span id="fysiekvoorkomenimgeo">Fysiekvoorkomenimgeo</span>
          </dt>
          <dd>{groenobjectEntity.fysiekvoorkomenimgeo}</dd>
          <dt>
            <span id="gewenstsluitingspercentage">Gewenstsluitingspercentage</span>
          </dt>
          <dd>{groenobjectEntity.gewenstsluitingspercentage}</dd>
          <dt>
            <span id="groenobjectbereikbaarheidplus">Groenobjectbereikbaarheidplus</span>
          </dt>
          <dd>{groenobjectEntity.groenobjectbereikbaarheidplus}</dd>
          <dt>
            <span id="groenobjectconstructielaag">Groenobjectconstructielaag</span>
          </dt>
          <dd>{groenobjectEntity.groenobjectconstructielaag}</dd>
          <dt>
            <span id="groenobjectrand">Groenobjectrand</span>
          </dt>
          <dd>{groenobjectEntity.groenobjectrand}</dd>
          <dt>
            <span id="groenobjectsoortnaam">Groenobjectsoortnaam</span>
          </dt>
          <dd>{groenobjectEntity.groenobjectsoortnaam}</dd>
          <dt>
            <span id="haagvoetlengte">Haagvoetlengte</span>
          </dt>
          <dd>{groenobjectEntity.haagvoetlengte}</dd>
          <dt>
            <span id="haagvoetoppervlakte">Haagvoetoppervlakte</span>
          </dt>
          <dd>{groenobjectEntity.haagvoetoppervlakte}</dd>
          <dt>
            <span id="herplantplicht">Herplantplicht</span>
          </dt>
          <dd>{groenobjectEntity.herplantplicht ? 'true' : 'false'}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{groenobjectEntity.hoogte}</dd>
          <dt>
            <span id="hoogteklassehaag">Hoogteklassehaag</span>
          </dt>
          <dd>{groenobjectEntity.hoogteklassehaag}</dd>
          <dt>
            <span id="knipfrequentie">Knipfrequentie</span>
          </dt>
          <dd>{groenobjectEntity.knipfrequentie}</dd>
          <dt>
            <span id="knipoppervlakte">Knipoppervlakte</span>
          </dt>
          <dd>{groenobjectEntity.knipoppervlakte}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{groenobjectEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{groenobjectEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{groenobjectEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{groenobjectEntity.leverancier}</dd>
          <dt>
            <span id="maaifrequentie">Maaifrequentie</span>
          </dt>
          <dd>{groenobjectEntity.maaifrequentie}</dd>
          <dt>
            <span id="maximalevalhoogte">Maximalevalhoogte</span>
          </dt>
          <dd>{groenobjectEntity.maximalevalhoogte}</dd>
          <dt>
            <span id="eobjectnummer">Eobjectnummer</span>
          </dt>
          <dd>{groenobjectEntity.eobjectnummer}</dd>
          <dt>
            <span id="obstakels">Obstakels</span>
          </dt>
          <dd>{groenobjectEntity.obstakels ? 'true' : 'false'}</dd>
          <dt>
            <span id="omtrek">Omtrek</span>
          </dt>
          <dd>{groenobjectEntity.omtrek}</dd>
          <dt>
            <span id="ondergroei">Ondergroei</span>
          </dt>
          <dd>{groenobjectEntity.ondergroei}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{groenobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="optalud">Optalud</span>
          </dt>
          <dd>{groenobjectEntity.optalud}</dd>
          <dt>
            <span id="taludsteilte">Taludsteilte</span>
          </dt>
          <dd>{groenobjectEntity.taludsteilte}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{groenobjectEntity.type}</dd>
          <dt>
            <span id="typebewerking">Typebewerking</span>
          </dt>
          <dd>{groenobjectEntity.typebewerking}</dd>
          <dt>
            <span id="typeomgevingsrisicoklasse">Typeomgevingsrisicoklasse</span>
          </dt>
          <dd>{groenobjectEntity.typeomgevingsrisicoklasse}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{groenobjectEntity.typeplus}</dd>
          <dt>
            <span id="typeplus2">Typeplus 2</span>
          </dt>
          <dd>{groenobjectEntity.typeplus2}</dd>
          <dt>
            <span id="veiligheidsklasseboom">Veiligheidsklasseboom</span>
          </dt>
          <dd>{groenobjectEntity.veiligheidsklasseboom}</dd>
        </dl>
        <Button tag={Link} to="/groenobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/groenobject/${groenobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GroenobjectDetail;
