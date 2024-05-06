import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './startformulieraanbesteden.reducer';

export const StartformulieraanbestedenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const startformulieraanbestedenEntity = useAppSelector(state => state.startformulieraanbesteden.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="startformulieraanbestedenDetailsHeading">Startformulieraanbesteden</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.id}</dd>
          <dt>
            <span id="beoogdelooptijd">Beoogdelooptijd</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.beoogdelooptijd}</dd>
          <dt>
            <span id="beoogdetotaleopdrachtwaarde">Beoogdetotaleopdrachtwaarde</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.beoogdetotaleopdrachtwaarde}</dd>
          <dt>
            <span id="indicatieaanvullendeopdrachtleverancier">Indicatieaanvullendeopdrachtleverancier</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatieaanvullendeopdrachtleverancier ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatiebeoogdeaanbestedingonderhands">Indicatiebeoogdeaanbestedingonderhands</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatiebeoogdeaanbestedingonderhands}</dd>
          <dt>
            <span id="indicatiebeoogdeprockomtovereen">Indicatiebeoogdeprockomtovereen</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatiebeoogdeprockomtovereen ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatieeenmaligelos">Indicatieeenmaligelos</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatieeenmaligelos}</dd>
          <dt>
            <span id="indicatiemeerjarigeraamovereenkomst">Indicatiemeerjarigeraamovereenkomst</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatiemeerjarigeraamovereenkomst ? 'true' : 'false'}</dd>
          <dt>
            <span id="indicatiemeerjarigrepeterend">Indicatiemeerjarigrepeterend</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatiemeerjarigrepeterend}</dd>
          <dt>
            <span id="indicatoroverkoepelendproject">Indicatoroverkoepelendproject</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.indicatoroverkoepelendproject}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.omschrijving}</dd>
          <dt>
            <span id="opdrachtcategorie">Opdrachtcategorie</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.opdrachtcategorie}</dd>
          <dt>
            <span id="opdrachtsoort">Opdrachtsoort</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.opdrachtsoort}</dd>
          <dt>
            <span id="toelichtingaanvullendeopdracht">Toelichtingaanvullendeopdracht</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.toelichtingaanvullendeopdracht}</dd>
          <dt>
            <span id="toelichtingeenmaligofrepeterend">Toelichtingeenmaligofrepeterend</span>
          </dt>
          <dd>{startformulieraanbestedenEntity.toelichtingeenmaligofrepeterend}</dd>
        </dl>
        <Button tag={Link} to="/startformulieraanbesteden" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/startformulieraanbesteden/${startformulieraanbestedenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StartformulieraanbestedenDetail;
