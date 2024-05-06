import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecomponent.reducer';

export const EcomponentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ecomponentEntity = useAppSelector(state => state.ecomponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecomponentDetailsHeading">Ecomponent</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ecomponentEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{ecomponentEntity.bedrag}</dd>
          <dt>
            <span id="datumbeginbetrekkingop">Datumbeginbetrekkingop</span>
          </dt>
          <dd>
            {ecomponentEntity.datumbeginbetrekkingop ? (
              <TextFormat value={ecomponentEntity.datumbeginbetrekkingop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindebetrekkingop">Datumeindebetrekkingop</span>
          </dt>
          <dd>
            {ecomponentEntity.datumeindebetrekkingop ? (
              <TextFormat value={ecomponentEntity.datumeindebetrekkingop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="debetcredit">Debetcredit</span>
          </dt>
          <dd>{ecomponentEntity.debetcredit}</dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{ecomponentEntity.groep}</dd>
          <dt>
            <span id="groepcode">Groepcode</span>
          </dt>
          <dd>{ecomponentEntity.groepcode}</dd>
          <dt>
            <span id="grootboekcode">Grootboekcode</span>
          </dt>
          <dd>{ecomponentEntity.grootboekcode}</dd>
          <dt>
            <span id="grootboekomschrijving">Grootboekomschrijving</span>
          </dt>
          <dd>{ecomponentEntity.grootboekomschrijving}</dd>
          <dt>
            <span id="kostenplaats">Kostenplaats</span>
          </dt>
          <dd>{ecomponentEntity.kostenplaats}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{ecomponentEntity.omschrijving}</dd>
          <dt>
            <span id="rekeningnummer">Rekeningnummer</span>
          </dt>
          <dd>{ecomponentEntity.rekeningnummer}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{ecomponentEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/ecomponent" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ecomponent/${ecomponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcomponentDetail;
