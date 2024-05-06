import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './instructieregel.reducer';

export const InstructieregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const instructieregelEntity = useAppSelector(state => state.instructieregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="instructieregelDetailsHeading">Instructieregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{instructieregelEntity.id}</dd>
          <dt>
            <span id="instructieregelinstrument">Instructieregelinstrument</span>
          </dt>
          <dd>{instructieregelEntity.instructieregelinstrument}</dd>
          <dt>
            <span id="instructieregeltaakuitoefening">Instructieregeltaakuitoefening</span>
          </dt>
          <dd>{instructieregelEntity.instructieregeltaakuitoefening}</dd>
          <dt>Beschrijftgebiedsaanwijzing Gebiedsaanwijzing</dt>
          <dd>
            {instructieregelEntity.beschrijftgebiedsaanwijzingGebiedsaanwijzings
              ? instructieregelEntity.beschrijftgebiedsaanwijzingGebiedsaanwijzings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {instructieregelEntity.beschrijftgebiedsaanwijzingGebiedsaanwijzings &&
                    i === instructieregelEntity.beschrijftgebiedsaanwijzingGebiedsaanwijzings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/instructieregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/instructieregel/${instructieregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InstructieregelDetail;
