import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './samensteller.reducer';

export const SamenstellerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const samenstellerEntity = useAppSelector(state => state.samensteller.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="samenstellerDetailsHeading">Samensteller</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{samenstellerEntity.id}</dd>
          <dt>
            <span id="rol">Rol</span>
          </dt>
          <dd>{samenstellerEntity.rol}</dd>
          <dt>Steltsamen Tentoonstelling</dt>
          <dd>
            {samenstellerEntity.steltsamenTentoonstellings
              ? samenstellerEntity.steltsamenTentoonstellings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {samenstellerEntity.steltsamenTentoonstellings && i === samenstellerEntity.steltsamenTentoonstellings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/samensteller" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/samensteller/${samenstellerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SamenstellerDetail;
