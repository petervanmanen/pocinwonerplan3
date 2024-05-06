import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './magazijnlocatie.reducer';

export const MagazijnlocatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const magazijnlocatieEntity = useAppSelector(state => state.magazijnlocatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="magazijnlocatieDetailsHeading">Magazijnlocatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{magazijnlocatieEntity.id}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{magazijnlocatieEntity.key}</dd>
          <dt>
            <span id="vaknummer">Vaknummer</span>
          </dt>
          <dd>{magazijnlocatieEntity.vaknummer}</dd>
          <dt>
            <span id="volgletter">Volgletter</span>
          </dt>
          <dd>{magazijnlocatieEntity.volgletter}</dd>
          <dt>Heeft Stelling</dt>
          <dd>{magazijnlocatieEntity.heeftStelling ? magazijnlocatieEntity.heeftStelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/magazijnlocatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/magazijnlocatie/${magazijnlocatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MagazijnlocatieDetail;
