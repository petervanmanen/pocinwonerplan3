import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zaaktype.reducer';

export const ZaaktypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zaaktypeEntity = useAppSelector(state => state.zaaktype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zaaktypeDetailsHeading">Zaaktype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zaaktypeEntity.id}</dd>
          <dt>
            <span id="archiefcode">Archiefcode</span>
          </dt>
          <dd>{zaaktypeEntity.archiefcode}</dd>
          <dt>
            <span id="datumbegingeldigheidzaaktype">Datumbegingeldigheidzaaktype</span>
          </dt>
          <dd>{zaaktypeEntity.datumbegingeldigheidzaaktype}</dd>
          <dt>
            <span id="datumeindegeldigheidzaaktype">Datumeindegeldigheidzaaktype</span>
          </dt>
          <dd>{zaaktypeEntity.datumeindegeldigheidzaaktype}</dd>
          <dt>
            <span id="doorlooptijdbehandeling">Doorlooptijdbehandeling</span>
          </dt>
          <dd>{zaaktypeEntity.doorlooptijdbehandeling}</dd>
          <dt>
            <span id="indicatiepublicatie">Indicatiepublicatie</span>
          </dt>
          <dd>{zaaktypeEntity.indicatiepublicatie}</dd>
          <dt>
            <span id="publicatietekst">Publicatietekst</span>
          </dt>
          <dd>{zaaktypeEntity.publicatietekst}</dd>
          <dt>
            <span id="servicenormbehandeling">Servicenormbehandeling</span>
          </dt>
          <dd>{zaaktypeEntity.servicenormbehandeling}</dd>
          <dt>
            <span id="trefwoord">Trefwoord</span>
          </dt>
          <dd>{zaaktypeEntity.trefwoord}</dd>
          <dt>
            <span id="vertrouwelijkaanduiding">Vertrouwelijkaanduiding</span>
          </dt>
          <dd>{zaaktypeEntity.vertrouwelijkaanduiding}</dd>
          <dt>
            <span id="zaakcategorie">Zaakcategorie</span>
          </dt>
          <dd>{zaaktypeEntity.zaakcategorie}</dd>
          <dt>
            <span id="zaaktypeomschrijving">Zaaktypeomschrijving</span>
          </dt>
          <dd>{zaaktypeEntity.zaaktypeomschrijving}</dd>
          <dt>
            <span id="zaaktypeomschrijvinggeneriek">Zaaktypeomschrijvinggeneriek</span>
          </dt>
          <dd>{zaaktypeEntity.zaaktypeomschrijvinggeneriek}</dd>
          <dt>Heeft Producttype</dt>
          <dd>{zaaktypeEntity.heeftProducttype ? zaaktypeEntity.heeftProducttype.id : ''}</dd>
          <dt>Betreft Product</dt>
          <dd>{zaaktypeEntity.betreftProduct ? zaaktypeEntity.betreftProduct.id : ''}</dd>
          <dt>Heeft Bedrijfsprocestype</dt>
          <dd>{zaaktypeEntity.heeftBedrijfsprocestype ? zaaktypeEntity.heeftBedrijfsprocestype.id : ''}</dd>
          <dt>Isverantwoordelijkevoor Medewerker</dt>
          <dd>{zaaktypeEntity.isverantwoordelijkevoorMedewerker ? zaaktypeEntity.isverantwoordelijkevoorMedewerker.id : ''}</dd>
          <dt>Isaanleidingvoor Formuliersoort</dt>
          <dd>
            {zaaktypeEntity.isaanleidingvoorFormuliersoorts
              ? zaaktypeEntity.isaanleidingvoorFormuliersoorts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {zaaktypeEntity.isaanleidingvoorFormuliersoorts && i === zaaktypeEntity.isaanleidingvoorFormuliersoorts.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/zaaktype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zaaktype/${zaaktypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZaaktypeDetail;
