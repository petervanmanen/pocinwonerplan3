import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './migratieingeschrevennatuurlijkpersoon.reducer';

export const MigratieingeschrevennatuurlijkpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const migratieingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.migratieingeschrevennatuurlijkpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="migratieingeschrevennatuurlijkpersoonDetailsHeading">Migratieingeschrevennatuurlijkpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{migratieingeschrevennatuurlijkpersoonEntity.id}</dd>
          <dt>
            <span id="aangevermigratie">Aangevermigratie</span>
          </dt>
          <dd>{migratieingeschrevennatuurlijkpersoonEntity.aangevermigratie}</dd>
          <dt>
            <span id="redenwijzigingmigratie">Redenwijzigingmigratie</span>
          </dt>
          <dd>{migratieingeschrevennatuurlijkpersoonEntity.redenwijzigingmigratie}</dd>
          <dt>
            <span id="soortmigratie">Soortmigratie</span>
          </dt>
          <dd>{migratieingeschrevennatuurlijkpersoonEntity.soortmigratie}</dd>
        </dl>
        <Button tag={Link} to="/migratieingeschrevennatuurlijkpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/migratieingeschrevennatuurlijkpersoon/${migratieingeschrevennatuurlijkpersoonEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MigratieingeschrevennatuurlijkpersoonDetail;
