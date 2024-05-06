import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zelfredzaamheidmatrix.reducer';

export const ZelfredzaamheidmatrixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zelfredzaamheidmatrixEntity = useAppSelector(state => state.zelfredzaamheidmatrix.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zelfredzaamheidmatrixDetailsHeading">Zelfredzaamheidmatrix</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{zelfredzaamheidmatrixEntity.id}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>{zelfredzaamheidmatrixEntity.datumeindegeldigheid}</dd>
          <dt>
            <span id="datumstartgeldigheid">Datumstartgeldigheid</span>
          </dt>
          <dd>{zelfredzaamheidmatrixEntity.datumstartgeldigheid}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{zelfredzaamheidmatrixEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{zelfredzaamheidmatrixEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/zelfredzaamheidmatrix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zelfredzaamheidmatrix/${zelfredzaamheidmatrixEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZelfredzaamheidmatrixDetail;
