import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rechtspersoon.reducer';

export const RechtspersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rechtspersoonEntity = useAppSelector(state => state.rechtspersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rechtspersoonDetailsHeading">Rechtspersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rechtspersoonEntity.id}</dd>
          <dt>
            <span id="adresbinnenland">Adresbinnenland</span>
          </dt>
          <dd>{rechtspersoonEntity.adresbinnenland}</dd>
          <dt>
            <span id="adresbuitenland">Adresbuitenland</span>
          </dt>
          <dd>{rechtspersoonEntity.adresbuitenland}</dd>
          <dt>
            <span id="adrescorrespondentie">Adrescorrespondentie</span>
          </dt>
          <dd>{rechtspersoonEntity.adrescorrespondentie}</dd>
          <dt>
            <span id="emailadres">Emailadres</span>
          </dt>
          <dd>{rechtspersoonEntity.emailadres}</dd>
          <dt>
            <span id="faxnummer">Faxnummer</span>
          </dt>
          <dd>{rechtspersoonEntity.faxnummer}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{rechtspersoonEntity.identificatie}</dd>
          <dt>
            <span id="kvknummer">Kvknummer</span>
          </dt>
          <dd>{rechtspersoonEntity.kvknummer}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{rechtspersoonEntity.naam}</dd>
          <dt>
            <span id="rechtsvorm">Rechtsvorm</span>
          </dt>
          <dd>{rechtspersoonEntity.rechtsvorm}</dd>
          <dt>
            <span id="rekeningnummer">Rekeningnummer</span>
          </dt>
          <dd>{rechtspersoonEntity.rekeningnummer}</dd>
          <dt>
            <span id="telefoonnummer">Telefoonnummer</span>
          </dt>
          <dd>{rechtspersoonEntity.telefoonnummer}</dd>
          <dt>Betrokkenen Kadastralemutatie</dt>
          <dd>
            {rechtspersoonEntity.betrokkenenKadastralemutaties
              ? rechtspersoonEntity.betrokkenenKadastralemutaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {rechtspersoonEntity.betrokkenenKadastralemutaties && i === rechtspersoonEntity.betrokkenenKadastralemutaties.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/rechtspersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rechtspersoon/${rechtspersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RechtspersoonDetail;
