import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kwalificatie.reducer';

export const KwalificatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kwalificatieEntity = useAppSelector(state => state.kwalificatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kwalificatieDetailsHeading">Kwalificatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kwalificatieEntity.id}</dd>
          <dt>
            <span id="eindegeldigheid">Eindegeldigheid</span>
          </dt>
          <dd>
            {kwalificatieEntity.eindegeldigheid ? (
              <TextFormat value={kwalificatieEntity.eindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startgeldigheid">Startgeldigheid</span>
          </dt>
          <dd>
            {kwalificatieEntity.startgeldigheid ? (
              <TextFormat value={kwalificatieEntity.startgeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Betreft Aanbesteding</dt>
          <dd>{kwalificatieEntity.betreftAanbesteding ? kwalificatieEntity.betreftAanbesteding.id : ''}</dd>
          <dt>Heeft Leverancier</dt>
          <dd>{kwalificatieEntity.heeftLeverancier ? kwalificatieEntity.heeftLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/kwalificatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kwalificatie/${kwalificatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KwalificatieDetail;
