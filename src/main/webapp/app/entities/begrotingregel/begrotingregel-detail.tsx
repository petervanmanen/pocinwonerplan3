import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './begrotingregel.reducer';

export const BegrotingregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const begrotingregelEntity = useAppSelector(state => state.begrotingregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="begrotingregelDetailsHeading">Begrotingregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{begrotingregelEntity.id}</dd>
          <dt>
            <span id="batenlasten">Batenlasten</span>
          </dt>
          <dd>{begrotingregelEntity.batenlasten}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{begrotingregelEntity.bedrag}</dd>
          <dt>
            <span id="soortregel">Soortregel</span>
          </dt>
          <dd>{begrotingregelEntity.soortregel}</dd>
          <dt>Betreft Doelstelling</dt>
          <dd>{begrotingregelEntity.betreftDoelstelling ? begrotingregelEntity.betreftDoelstelling.id : ''}</dd>
          <dt>Betreft Product</dt>
          <dd>{begrotingregelEntity.betreftProduct ? begrotingregelEntity.betreftProduct.id : ''}</dd>
          <dt>Betreft Kostenplaats</dt>
          <dd>{begrotingregelEntity.betreftKostenplaats ? begrotingregelEntity.betreftKostenplaats.id : ''}</dd>
          <dt>Betreft Hoofdrekening</dt>
          <dd>{begrotingregelEntity.betreftHoofdrekening ? begrotingregelEntity.betreftHoofdrekening.id : ''}</dd>
          <dt>Betreft Hoofdstuk</dt>
          <dd>{begrotingregelEntity.betreftHoofdstuk ? begrotingregelEntity.betreftHoofdstuk.id : ''}</dd>
          <dt>Heeft Begroting</dt>
          <dd>{begrotingregelEntity.heeftBegroting ? begrotingregelEntity.heeftBegroting.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/begrotingregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/begrotingregel/${begrotingregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BegrotingregelDetail;
