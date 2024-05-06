import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './normwaarde.reducer';

export const NormwaardeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const normwaardeEntity = useAppSelector(state => state.normwaarde.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="normwaardeDetailsHeading">Normwaarde</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{normwaardeEntity.id}</dd>
          <dt>
            <span id="kwalitatievewaarde">Kwalitatievewaarde</span>
          </dt>
          <dd>{normwaardeEntity.kwalitatievewaarde}</dd>
          <dt>
            <span id="kwantitatievewaardeeenheid">Kwantitatievewaardeeenheid</span>
          </dt>
          <dd>{normwaardeEntity.kwantitatievewaardeeenheid}</dd>
          <dt>
            <span id="kwantitatievewaardeomvang">Kwantitatievewaardeomvang</span>
          </dt>
          <dd>{normwaardeEntity.kwantitatievewaardeomvang}</dd>
          <dt>Geldtvoor Locatie</dt>
          <dd>
            {normwaardeEntity.geldtvoorLocaties
              ? normwaardeEntity.geldtvoorLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {normwaardeEntity.geldtvoorLocaties && i === normwaardeEntity.geldtvoorLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Bevat Norm</dt>
          <dd>{normwaardeEntity.bevatNorm ? normwaardeEntity.bevatNorm.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/normwaarde" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/normwaarde/${normwaardeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NormwaardeDetail;
