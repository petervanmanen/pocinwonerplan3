import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './landofgebied.reducer';

export const LandofgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const landofgebiedEntity = useAppSelector(state => state.landofgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="landofgebiedDetailsHeading">Landofgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{landofgebiedEntity.id}</dd>
          <dt>
            <span id="datumeindeland">Datumeindeland</span>
          </dt>
          <dd>
            {landofgebiedEntity.datumeindeland ? (
              <TextFormat value={landofgebiedEntity.datumeindeland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangland">Datumingangland</span>
          </dt>
          <dd>
            {landofgebiedEntity.datumingangland ? (
              <TextFormat value={landofgebiedEntity.datumingangland} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="landcode">Landcode</span>
          </dt>
          <dd>{landofgebiedEntity.landcode}</dd>
          <dt>
            <span id="landcodeiso">Landcodeiso</span>
          </dt>
          <dd>{landofgebiedEntity.landcodeiso}</dd>
          <dt>
            <span id="landnaam">Landnaam</span>
          </dt>
          <dd>{landofgebiedEntity.landnaam}</dd>
        </dl>
        <Button tag={Link} to="/landofgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/landofgebied/${landofgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LandofgebiedDetail;
