import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wozdeelobject.reducer';

export const WozdeelobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wozdeelobjectEntity = useAppSelector(state => state.wozdeelobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wozdeelobjectDetailsHeading">Wozdeelobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wozdeelobjectEntity.id}</dd>
          <dt>
            <span id="codewozdeelobject">Codewozdeelobject</span>
          </dt>
          <dd>{wozdeelobjectEntity.codewozdeelobject}</dd>
          <dt>
            <span id="datumbegingeldigheiddeelobject">Datumbegingeldigheiddeelobject</span>
          </dt>
          <dd>
            {wozdeelobjectEntity.datumbegingeldigheiddeelobject ? (
              <TextFormat value={wozdeelobjectEntity.datumbegingeldigheiddeelobject} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheiddeelobject">Datumeindegeldigheiddeelobject</span>
          </dt>
          <dd>
            {wozdeelobjectEntity.datumeindegeldigheiddeelobject ? (
              <TextFormat value={wozdeelobjectEntity.datumeindegeldigheiddeelobject} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="statuswozdeelobject">Statuswozdeelobject</span>
          </dt>
          <dd>{wozdeelobjectEntity.statuswozdeelobject}</dd>
          <dt>
            <span id="wozdeelobjectnummer">Wozdeelobjectnummer</span>
          </dt>
          <dd>{wozdeelobjectEntity.wozdeelobjectnummer}</dd>
        </dl>
        <Button tag={Link} to="/wozdeelobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wozdeelobject/${wozdeelobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WozdeelobjectDetail;
