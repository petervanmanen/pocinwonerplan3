import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ingeschrevenpersoon.reducer';

export const IngeschrevenpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ingeschrevenpersoonEntity = useAppSelector(state => state.ingeschrevenpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ingeschrevenpersoonDetailsHeading">Ingeschrevenpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.id}</dd>
          <dt>
            <span id="adresherkomst">Adresherkomst</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.adresherkomst}</dd>
          <dt>
            <span id="anummer">Anummer</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.anummer}</dd>
          <dt>
            <span id="beschrijvinglocatie">Beschrijvinglocatie</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.beschrijvinglocatie}</dd>
          <dt>
            <span id="buitenlandsreisdocument">Buitenlandsreisdocument</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.buitenlandsreisdocument}</dd>
          <dt>
            <span id="burgerlijkestaat">Burgerlijkestaat</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.burgerlijkestaat}</dd>
          <dt>
            <span id="datumbegingeldigheidverblijfplaats">Datumbegingeldigheidverblijfplaats</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.datumbegingeldigheidverblijfplaats}</dd>
          <dt>
            <span id="datumeindegeldigheidverblijfsplaats">Datumeindegeldigheidverblijfsplaats</span>
          </dt>
          <dd>
            {ingeschrevenpersoonEntity.datumeindegeldigheidverblijfsplaats ? (
              <TextFormat
                value={ingeschrevenpersoonEntity.datumeindegeldigheidverblijfsplaats}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datuminschrijvinggemeente">Datuminschrijvinggemeente</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.datuminschrijvinggemeente}</dd>
          <dt>
            <span id="datumopschortingbijhouding">Datumopschortingbijhouding</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.datumopschortingbijhouding}</dd>
          <dt>
            <span id="datumvertrekuitnederland">Datumvertrekuitnederland</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.datumvertrekuitnederland}</dd>
          <dt>
            <span id="datumvestigingnederland">Datumvestigingnederland</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.datumvestigingnederland}</dd>
          <dt>
            <span id="gemeentevaninschrijving">Gemeentevaninschrijving</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.gemeentevaninschrijving}</dd>
          <dt>
            <span id="gezinsrelatie">Gezinsrelatie</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.gezinsrelatie}</dd>
          <dt>
            <span id="indicatiegeheim">Indicatiegeheim</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.indicatiegeheim}</dd>
          <dt>
            <span id="ingezetene">Ingezetene</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.ingezetene}</dd>
          <dt>
            <span id="landwaarnaarvertrokken">Landwaarnaarvertrokken</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.landwaarnaarvertrokken}</dd>
          <dt>
            <span id="landwaarvandaaningeschreven">Landwaarvandaaningeschreven</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.landwaarvandaaningeschreven}</dd>
          <dt>
            <span id="ouder1">Ouder 1</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.ouder1}</dd>
          <dt>
            <span id="ouder2">Ouder 2</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.ouder2}</dd>
          <dt>
            <span id="partnerid">Partnerid</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.partnerid}</dd>
          <dt>
            <span id="redeneindebewoning">Redeneindebewoning</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.redeneindebewoning}</dd>
          <dt>
            <span id="redenopschortingbijhouding">Redenopschortingbijhouding</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.redenopschortingbijhouding}</dd>
          <dt>
            <span id="signaleringreisdocument">Signaleringreisdocument</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.signaleringreisdocument}</dd>
          <dt>
            <span id="verblijfstitel">Verblijfstitel</span>
          </dt>
          <dd>{ingeschrevenpersoonEntity.verblijfstitel}</dd>
        </dl>
        <Button tag={Link} to="/ingeschrevenpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ingeschrevenpersoon/${ingeschrevenpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IngeschrevenpersoonDetail;
