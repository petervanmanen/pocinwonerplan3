import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wozdeelobjectcode.reducer';

export const WozdeelobjectcodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wozdeelobjectcodeEntity = useAppSelector(state => state.wozdeelobjectcode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wozdeelobjectcodeDetailsHeading">Wozdeelobjectcode</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wozdeelobjectcodeEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheiddeelojectcode">Datumbegingeldigheiddeelojectcode</span>
          </dt>
          <dd>
            {wozdeelobjectcodeEntity.datumbegingeldigheiddeelojectcode ? (
              <TextFormat value={wozdeelobjectcodeEntity.datumbegingeldigheiddeelojectcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheiddeelobjectcode">Datumeindegeldigheiddeelobjectcode</span>
          </dt>
          <dd>
            {wozdeelobjectcodeEntity.datumeindegeldigheiddeelobjectcode ? (
              <TextFormat value={wozdeelobjectcodeEntity.datumeindegeldigheiddeelobjectcode} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deelobjectcode">Deelobjectcode</span>
          </dt>
          <dd>{wozdeelobjectcodeEntity.deelobjectcode}</dd>
          <dt>
            <span id="naamdeelobjectcode">Naamdeelobjectcode</span>
          </dt>
          <dd>{wozdeelobjectcodeEntity.naamdeelobjectcode}</dd>
        </dl>
        <Button tag={Link} to="/wozdeelobjectcode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wozdeelobjectcode/${wozdeelobjectcodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WozdeelobjectcodeDetail;
