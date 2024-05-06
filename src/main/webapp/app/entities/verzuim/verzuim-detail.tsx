import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verzuim.reducer';

export const VerzuimDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verzuimEntity = useAppSelector(state => state.verzuim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verzuimDetailsHeading">Verzuim</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verzuimEntity.id}</dd>
          <dt>
            <span id="datumtijdeinde">Datumtijdeinde</span>
          </dt>
          <dd>{verzuimEntity.datumtijdeinde}</dd>
          <dt>
            <span id="datumtijdstart">Datumtijdstart</span>
          </dt>
          <dd>{verzuimEntity.datumtijdstart}</dd>
          <dt>Soortverzuim Verzuimsoort</dt>
          <dd>{verzuimEntity.soortverzuimVerzuimsoort ? verzuimEntity.soortverzuimVerzuimsoort.id : ''}</dd>
          <dt>Heeftverzuim Werknemer</dt>
          <dd>{verzuimEntity.heeftverzuimWerknemer ? verzuimEntity.heeftverzuimWerknemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verzuim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verzuim/${verzuimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerzuimDetail;
