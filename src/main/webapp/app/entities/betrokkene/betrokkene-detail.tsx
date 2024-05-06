import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './betrokkene.reducer';

export const BetrokkeneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const betrokkeneEntity = useAppSelector(state => state.betrokkene.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="betrokkeneDetailsHeading">Betrokkene</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{betrokkeneEntity.id}</dd>
          <dt>
            <span id="adresbinnenland">Adresbinnenland</span>
          </dt>
          <dd>{betrokkeneEntity.adresbinnenland}</dd>
          <dt>
            <span id="adresbuitenland">Adresbuitenland</span>
          </dt>
          <dd>{betrokkeneEntity.adresbuitenland}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{betrokkeneEntity.identificatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{betrokkeneEntity.naam}</dd>
          <dt>
            <span id="rol">Rol</span>
          </dt>
          <dd>{betrokkeneEntity.rol}</dd>
          <dt>Is Medewerker</dt>
          <dd>{betrokkeneEntity.isMedewerker ? betrokkeneEntity.isMedewerker.id : ''}</dd>
          <dt>Oefentuit Zaak</dt>
          <dd>
            {betrokkeneEntity.oefentuitZaaks
              ? betrokkeneEntity.oefentuitZaaks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {betrokkeneEntity.oefentuitZaaks && i === betrokkeneEntity.oefentuitZaaks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/betrokkene" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/betrokkene/${betrokkeneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BetrokkeneDetail;
