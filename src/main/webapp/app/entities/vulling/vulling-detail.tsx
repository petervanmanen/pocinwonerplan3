import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vulling.reducer';

export const VullingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vullingEntity = useAppSelector(state => state.vulling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vullingDetailsHeading">Vulling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vullingEntity.id}</dd>
          <dt>
            <span id="grondsoort">Grondsoort</span>
          </dt>
          <dd>{vullingEntity.grondsoort}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{vullingEntity.key}</dd>
          <dt>
            <span id="keyspoor">Keyspoor</span>
          </dt>
          <dd>{vullingEntity.keyspoor}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{vullingEntity.kleur}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{vullingEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{vullingEntity.putnummer}</dd>
          <dt>
            <span id="spoornummer">Spoornummer</span>
          </dt>
          <dd>{vullingEntity.spoornummer}</dd>
          <dt>
            <span id="structuur">Structuur</span>
          </dt>
          <dd>{vullingEntity.structuur}</dd>
          <dt>
            <span id="vlaknummer">Vlaknummer</span>
          </dt>
          <dd>{vullingEntity.vlaknummer}</dd>
          <dt>
            <span id="vullingnummer">Vullingnummer</span>
          </dt>
          <dd>{vullingEntity.vullingnummer}</dd>
          <dt>Heeft Spoor</dt>
          <dd>{vullingEntity.heeftSpoor ? vullingEntity.heeftSpoor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vulling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vulling/${vullingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VullingDetail;
