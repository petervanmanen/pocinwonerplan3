import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kpclassaclassc.reducer';

export const KpclassaclasscDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kpclassaclasscEntity = useAppSelector(state => state.kpclassaclassc.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kpclassaclasscDetailsHeading">Kpclassaclassc</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kpclassaclasscEntity.id}</dd>
          <dt>
            <span id="mbronsysteem">Mbronsysteem</span>
          </dt>
          <dd>{kpclassaclasscEntity.mbronsysteem}</dd>
          <dt>
            <span id="mdatumtijdgeladen">Mdatumtijdgeladen</span>
          </dt>
          <dd>{kpclassaclasscEntity.mdatumtijdgeladen}</dd>
          <dt>
            <span id="classcid">Classcid</span>
          </dt>
          <dd>{kpclassaclasscEntity.classcid}</dd>
          <dt>
            <span id="classaid">Classaid</span>
          </dt>
          <dd>{kpclassaclasscEntity.classaid}</dd>
        </dl>
        <Button tag={Link} to="/kpclassaclassc" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kpclassaclassc/${kpclassaclasscEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KpclassaclasscDetail;
