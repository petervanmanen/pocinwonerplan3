import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verzoekomtoewijzing.reducer';

export const VerzoekomtoewijzingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verzoekomtoewijzingEntity = useAppSelector(state => state.verzoekomtoewijzing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verzoekomtoewijzingDetailsHeading">Verzoekomtoewijzing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.id}</dd>
          <dt>
            <span id="beschikkingsnummer">Beschikkingsnummer</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.beschikkingsnummer}</dd>
          <dt>
            <span id="commentaar">Commentaar</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.commentaar}</dd>
          <dt>
            <span id="datumeindetoewijzing">Datumeindetoewijzing</span>
          </dt>
          <dd>
            {verzoekomtoewijzingEntity.datumeindetoewijzing ? (
              <TextFormat value={verzoekomtoewijzingEntity.datumeindetoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangbeschikking">Datumingangbeschikking</span>
          </dt>
          <dd>
            {verzoekomtoewijzingEntity.datumingangbeschikking ? (
              <TextFormat value={verzoekomtoewijzingEntity.datumingangbeschikking} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingangtoewijzing">Datumingangtoewijzing</span>
          </dt>
          <dd>
            {verzoekomtoewijzingEntity.datumingangtoewijzing ? (
              <TextFormat value={verzoekomtoewijzingEntity.datumingangtoewijzing} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumontvangst">Datumontvangst</span>
          </dt>
          <dd>
            {verzoekomtoewijzingEntity.datumontvangst ? (
              <TextFormat value={verzoekomtoewijzingEntity.datumontvangst} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eenheid">Eenheid</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.eenheid}</dd>
          <dt>
            <span id="frequentie">Frequentie</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.frequentie}</dd>
          <dt>
            <span id="raamcontract">Raamcontract</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.raamcontract ? 'true' : 'false'}</dd>
          <dt>
            <span id="referentieaanbieder">Referentieaanbieder</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.referentieaanbieder}</dd>
          <dt>
            <span id="soortverwijzer">Soortverwijzer</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.soortverwijzer}</dd>
          <dt>
            <span id="verwijzer">Verwijzer</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.verwijzer}</dd>
          <dt>
            <span id="volume">Volume</span>
          </dt>
          <dd>{verzoekomtoewijzingEntity.volume}</dd>
        </dl>
        <Button tag={Link} to="/verzoekomtoewijzing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verzoekomtoewijzing/${verzoekomtoewijzingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerzoekomtoewijzingDetail;
