import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './boom.reducer';

export const BoomDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const boomEntity = useAppSelector(state => state.boom.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="boomDetailsHeading">Boom</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{boomEntity.id}</dd>
          <dt>
            <span id="beleidsstatus">Beleidsstatus</span>
          </dt>
          <dd>{boomEntity.beleidsstatus}</dd>
          <dt>
            <span id="beoogdeomlooptijd">Beoogdeomlooptijd</span>
          </dt>
          <dd>{boomEntity.beoogdeomlooptijd}</dd>
          <dt>
            <span id="boombeeld">Boombeeld</span>
          </dt>
          <dd>{boomEntity.boombeeld}</dd>
          <dt>
            <span id="boombeschermer">Boombeschermer</span>
          </dt>
          <dd>{boomEntity.boombeschermer}</dd>
          <dt>
            <span id="boomgroep">Boomgroep</span>
          </dt>
          <dd>{boomEntity.boomgroep}</dd>
          <dt>
            <span id="boomhoogteactueel">Boomhoogteactueel</span>
          </dt>
          <dd>{boomEntity.boomhoogteactueel}</dd>
          <dt>
            <span id="boomhoogteklasseactueel">Boomhoogteklasseactueel</span>
          </dt>
          <dd>{boomEntity.boomhoogteklasseactueel}</dd>
          <dt>
            <span id="boomhoogteklasseeindebeeld">Boomhoogteklasseeindebeeld</span>
          </dt>
          <dd>{boomEntity.boomhoogteklasseeindebeeld}</dd>
          <dt>
            <span id="boomspiegel">Boomspiegel</span>
          </dt>
          <dd>{boomEntity.boomspiegel}</dd>
          <dt>
            <span id="boomtypebeschermingsstatusplus">Boomtypebeschermingsstatusplus</span>
          </dt>
          <dd>{boomEntity.boomtypebeschermingsstatusplus}</dd>
          <dt>
            <span id="boomvoorziening">Boomvoorziening</span>
          </dt>
          <dd>{boomEntity.boomvoorziening}</dd>
          <dt>
            <span id="controlefrequentie">Controlefrequentie</span>
          </dt>
          <dd>{boomEntity.controlefrequentie}</dd>
          <dt>
            <span id="feestverlichting">Feestverlichting</span>
          </dt>
          <dd>{boomEntity.feestverlichting}</dd>
          <dt>
            <span id="groeifase">Groeifase</span>
          </dt>
          <dd>{boomEntity.groeifase}</dd>
          <dt>
            <span id="groeiplaatsinrichting">Groeiplaatsinrichting</span>
          </dt>
          <dd>{boomEntity.groeiplaatsinrichting}</dd>
          <dt>
            <span id="herplantplicht">Herplantplicht</span>
          </dt>
          <dd>{boomEntity.herplantplicht ? 'true' : 'false'}</dd>
          <dt>
            <span id="kiemjaar">Kiemjaar</span>
          </dt>
          <dd>{boomEntity.kiemjaar}</dd>
          <dt>
            <span id="kroondiameterklasseactueel">Kroondiameterklasseactueel</span>
          </dt>
          <dd>{boomEntity.kroondiameterklasseactueel}</dd>
          <dt>
            <span id="kroondiameterklasseeindebeeld">Kroondiameterklasseeindebeeld</span>
          </dt>
          <dd>{boomEntity.kroondiameterklasseeindebeeld}</dd>
          <dt>
            <span id="kroonvolume">Kroonvolume</span>
          </dt>
          <dd>{boomEntity.kroonvolume}</dd>
          <dt>
            <span id="leeftijd">Leeftijd</span>
          </dt>
          <dd>{boomEntity.leeftijd}</dd>
          <dt>
            <span id="meerstammig">Meerstammig</span>
          </dt>
          <dd>{boomEntity.meerstammig ? 'true' : 'false'}</dd>
          <dt>
            <span id="monetaireboomwaarde">Monetaireboomwaarde</span>
          </dt>
          <dd>{boomEntity.monetaireboomwaarde}</dd>
          <dt>
            <span id="snoeifase">Snoeifase</span>
          </dt>
          <dd>{boomEntity.snoeifase}</dd>
          <dt>
            <span id="stamdiameter">Stamdiameter</span>
          </dt>
          <dd>{boomEntity.stamdiameter}</dd>
          <dt>
            <span id="stamdiameterklasse">Stamdiameterklasse</span>
          </dt>
          <dd>{boomEntity.stamdiameterklasse}</dd>
          <dt>
            <span id="takvrijeruimtetotgebouw">Takvrijeruimtetotgebouw</span>
          </dt>
          <dd>{boomEntity.takvrijeruimtetotgebouw}</dd>
          <dt>
            <span id="takvrijestam">Takvrijestam</span>
          </dt>
          <dd>{boomEntity.takvrijestam}</dd>
          <dt>
            <span id="takvrijezoneprimair">Takvrijezoneprimair</span>
          </dt>
          <dd>{boomEntity.takvrijezoneprimair}</dd>
          <dt>
            <span id="takvrijezonesecundair">Takvrijezonesecundair</span>
          </dt>
          <dd>{boomEntity.takvrijezonesecundair}</dd>
          <dt>
            <span id="transponder">Transponder</span>
          </dt>
          <dd>{boomEntity.transponder}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{boomEntity.type}</dd>
          <dt>
            <span id="typebeschermingsstatus">Typebeschermingsstatus</span>
          </dt>
          <dd>{boomEntity.typebeschermingsstatus}</dd>
          <dt>
            <span id="typeomgevingsrisicoklasse">Typeomgevingsrisicoklasse</span>
          </dt>
          <dd>{boomEntity.typeomgevingsrisicoklasse}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{boomEntity.typeplus}</dd>
          <dt>
            <span id="typevermeerderingsvorm">Typevermeerderingsvorm</span>
          </dt>
          <dd>{boomEntity.typevermeerderingsvorm}</dd>
          <dt>
            <span id="veiligheidsklasseboom">Veiligheidsklasseboom</span>
          </dt>
          <dd>{boomEntity.veiligheidsklasseboom}</dd>
          <dt>
            <span id="verplant">Verplant</span>
          </dt>
          <dd>{boomEntity.verplant ? 'true' : 'false'}</dd>
          <dt>
            <span id="verplantbaar">Verplantbaar</span>
          </dt>
          <dd>{boomEntity.verplantbaar ? 'true' : 'false'}</dd>
          <dt>
            <span id="vrijedoorrijhoogte">Vrijedoorrijhoogte</span>
          </dt>
          <dd>{boomEntity.vrijedoorrijhoogte}</dd>
          <dt>
            <span id="vrijedoorrijhoogteprimair">Vrijedoorrijhoogteprimair</span>
          </dt>
          <dd>{boomEntity.vrijedoorrijhoogteprimair}</dd>
          <dt>
            <span id="vrijedoorrijhoogtesecundair">Vrijedoorrijhoogtesecundair</span>
          </dt>
          <dd>{boomEntity.vrijedoorrijhoogtesecundair}</dd>
          <dt>
            <span id="vrijetakval">Vrijetakval</span>
          </dt>
          <dd>{boomEntity.vrijetakval}</dd>
        </dl>
        <Button tag={Link} to="/boom" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/boom/${boomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BoomDetail;
