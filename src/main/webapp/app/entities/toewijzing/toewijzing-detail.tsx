import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './toewijzing.reducer';

export const ToewijzingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const toewijzingEntity = useAppSelector(state => state.toewijzing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="toewijzingDetailsHeading">Toewijzing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{toewijzingEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{toewijzingEntity.code}</dd>
          <dt>
            <span id="commentaar">Commentaar</span>
          </dt>
          <dd>{toewijzingEntity.commentaar}</dd>
          <dt>
            <span id="datumaanschaf">Datumaanschaf</span>
          </dt>
          <dd>
            {toewijzingEntity.datumaanschaf ? (
              <TextFormat value={toewijzingEntity.datumaanschaf} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindetoewijzing">Datumeindetoewijzing</span>
          </dt>
          <dd>
            {toewijzingEntity.datumeindetoewijzing ? (
              <TextFormat value={toewijzingEntity.datumeindetoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstarttoewijzing">Datumstarttoewijzing</span>
          </dt>
          <dd>
            {toewijzingEntity.datumstarttoewijzing ? (
              <TextFormat value={toewijzingEntity.datumstarttoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtoewijzing">Datumtoewijzing</span>
          </dt>
          <dd>
            {toewijzingEntity.datumtoewijzing ? (
              <TextFormat value={toewijzingEntity.datumtoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{toewijzingEntity.eenheid}</dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{toewijzingEntity.frequentie}</dd>
          <dt>
            <span id="omvang">Omvang</span>
          </dt>
          <dd>{toewijzingEntity.omvang}</dd>
          <dt>
            <span id="redenwijziging">Redenwijziging</span>
          </dt>
          <dd>{toewijzingEntity.redenwijziging}</dd>
          <dt>
            <span id="toewijzingnummer">Toewijzingnummer</span>
          </dt>
          <dd>{toewijzingEntity.toewijzingnummer}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{toewijzingEntity.wet}</dd>
          <dt>Levertvoorziening Leverancier</dt>
          <dd>{toewijzingEntity.levertvoorzieningLeverancier ? toewijzingEntity.levertvoorzieningLeverancier.id : ''}</dd>
          <dt>Toewijzing Beschikking</dt>
          <dd>{toewijzingEntity.toewijzingBeschikking ? toewijzingEntity.toewijzingBeschikking.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/toewijzing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/toewijzing/${toewijzingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ToewijzingDetail;
