import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vindplaats.reducer';

export const VindplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vindplaatsEntity = useAppSelector(state => state.vindplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vindplaatsDetailsHeading">Vindplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vindplaatsEntity.id}</dd>
          <dt>
            <span id="aard">Aard</span>
          </dt>
          <dd>{vindplaatsEntity.aard}</dd>
          <dt>
            <span id="begindatering">Begindatering</span>
          </dt>
          <dd>{vindplaatsEntity.begindatering}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{vindplaatsEntity.beschrijving}</dd>
          <dt>
            <span id="bibliografie">Bibliografie</span>
          </dt>
          <dd>{vindplaatsEntity.bibliografie}</dd>
          <dt>
            <span id="datering">Datering</span>
          </dt>
          <dd>{vindplaatsEntity.datering}</dd>
          <dt>
            <span id="depot">Depot</span>
          </dt>
          <dd>{vindplaatsEntity.depot}</dd>
          <dt>
            <span id="documentatie">Documentatie</span>
          </dt>
          <dd>{vindplaatsEntity.documentatie}</dd>
          <dt>
            <span id="einddatering">Einddatering</span>
          </dt>
          <dd>{vindplaatsEntity.einddatering}</dd>
          <dt>
            <span id="gemeente">Gemeente</span>
          </dt>
          <dd>{vindplaatsEntity.gemeente}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{vindplaatsEntity.locatie}</dd>
          <dt>
            <span id="mobilia">Mobilia</span>
          </dt>
          <dd>{vindplaatsEntity.mobilia}</dd>
          <dt>
            <span id="onderzoek">Onderzoek</span>
          </dt>
          <dd>{vindplaatsEntity.onderzoek}</dd>
          <dt>
            <span id="projectcode">Projectcode</span>
          </dt>
          <dd>{vindplaatsEntity.projectcode}</dd>
          <dt>
            <span id="vindplaats">Vindplaats</span>
          </dt>
          <dd>{vindplaatsEntity.vindplaats}</dd>
          <dt>Hoortbij Project</dt>
          <dd>{vindplaatsEntity.hoortbijProject ? vindplaatsEntity.hoortbijProject.id : ''}</dd>
          <dt>Istevindenin Depot</dt>
          <dd>{vindplaatsEntity.istevindeninDepot ? vindplaatsEntity.istevindeninDepot.id : ''}</dd>
          <dt>Istevindenin Kast</dt>
          <dd>{vindplaatsEntity.istevindeninKast ? vindplaatsEntity.istevindeninKast.id : ''}</dd>
          <dt>Istevindenin Plank</dt>
          <dd>{vindplaatsEntity.istevindeninPlank ? vindplaatsEntity.istevindeninPlank.id : ''}</dd>
          <dt>Istevindenin Stelling</dt>
          <dd>{vindplaatsEntity.istevindeninStelling ? vindplaatsEntity.istevindeninStelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vindplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vindplaats/${vindplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VindplaatsDetail;
