import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vondst.reducer';

export const VondstDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vondstEntity = useAppSelector(state => state.vondst.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vondstDetailsHeading">Vondst</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vondstEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{vondstEntity.datum ? <TextFormat value={vondstEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{vondstEntity.key}</dd>
          <dt>
            <span id="keyvulling">Keyvulling</span>
          </dt>
          <dd>{vondstEntity.keyvulling}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{vondstEntity.omschrijving}</dd>
          <dt>
            <span id="omstandigheden">Omstandigheden</span>
          </dt>
          <dd>{vondstEntity.omstandigheden}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{vondstEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{vondstEntity.putnummer}</dd>
          <dt>
            <span id="spoornummer">Spoornummer</span>
          </dt>
          <dd>{vondstEntity.spoornummer}</dd>
          <dt>
            <span id="vlaknummer">Vlaknummer</span>
          </dt>
          <dd>{vondstEntity.vlaknummer}</dd>
          <dt>
            <span id="vondstnummer">Vondstnummer</span>
          </dt>
          <dd>{vondstEntity.vondstnummer}</dd>
          <dt>
            <span id="vullingnummer">Vullingnummer</span>
          </dt>
          <dd>{vondstEntity.vullingnummer}</dd>
          <dt>
            <span id="xcoordinaat">Xcoordinaat</span>
          </dt>
          <dd>{vondstEntity.xcoordinaat}</dd>
          <dt>
            <span id="ycoordinaat">Ycoordinaat</span>
          </dt>
          <dd>{vondstEntity.ycoordinaat}</dd>
          <dt>Heeft Vulling</dt>
          <dd>{vondstEntity.heeftVulling ? vondstEntity.heeftVulling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vondst" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vondst/${vondstEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VondstDetail;
