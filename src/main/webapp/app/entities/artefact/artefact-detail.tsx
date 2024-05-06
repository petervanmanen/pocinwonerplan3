import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artefact.reducer';

export const ArtefactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artefactEntity = useAppSelector(state => state.artefact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artefactDetailsHeading">Artefact</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artefactEntity.id}</dd>
          <dt>
            <span id="artefectnummer">Artefectnummer</span>
          </dt>
          <dd>{artefactEntity.artefectnummer}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{artefactEntity.beschrijving}</dd>
          <dt>
            <span id="conserveren">Conserveren</span>
          </dt>
          <dd>{artefactEntity.conserveren ? 'true' : 'false'}</dd>
          <dt>
            <span id="datering">Datering</span>
          </dt>
          <dd>{artefactEntity.datering}</dd>
          <dt>
            <span id="dateringcomplex">Dateringcomplex</span>
          </dt>
          <dd>{artefactEntity.dateringcomplex}</dd>
          <dt>
            <span id="determinatieniveau">Determinatieniveau</span>
          </dt>
          <dd>{artefactEntity.determinatieniveau}</dd>
          <dt>
            <span id="dianummer">Dianummer</span>
          </dt>
          <dd>{artefactEntity.dianummer}</dd>
          <dt>
            <span id="doosnummer">Doosnummer</span>
          </dt>
          <dd>{artefactEntity.doosnummer}</dd>
          <dt>
            <span id="exposabel">Exposabel</span>
          </dt>
          <dd>{artefactEntity.exposabel ? 'true' : 'false'}</dd>
          <dt>
            <span id="fotonummer">Fotonummer</span>
          </dt>
          <dd>{artefactEntity.fotonummer}</dd>
          <dt>
            <span id="functie">Functie</span>
          </dt>
          <dd>{artefactEntity.functie}</dd>
          <dt>
            <span id="herkomst">Herkomst</span>
          </dt>
          <dd>{artefactEntity.herkomst}</dd>
          <dt>
            <span id="key">Key</span>
          </dt>
          <dd>{artefactEntity.key}</dd>
          <dt>
            <span id="keydoos">Keydoos</span>
          </dt>
          <dd>{artefactEntity.keydoos}</dd>
          <dt>
            <span id="keymagazijnplaatsing">Keymagazijnplaatsing</span>
          </dt>
          <dd>{artefactEntity.keymagazijnplaatsing}</dd>
          <dt>
            <span id="keyput">Keyput</span>
          </dt>
          <dd>{artefactEntity.keyput}</dd>
          <dt>
            <span id="keyvondst">Keyvondst</span>
          </dt>
          <dd>{artefactEntity.keyvondst}</dd>
          <dt>
            <span id="literatuur">Literatuur</span>
          </dt>
          <dd>{artefactEntity.literatuur}</dd>
          <dt>
            <span id="maten">Maten</span>
          </dt>
          <dd>{artefactEntity.maten}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{artefactEntity.naam}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{artefactEntity.opmerkingen}</dd>
          <dt>
            <span id="origine">Origine</span>
          </dt>
          <dd>{artefactEntity.origine}</dd>
          <dt>
            <span id="projectcd">Projectcd</span>
          </dt>
          <dd>{artefactEntity.projectcd}</dd>
          <dt>
            <span id="putnummer">Putnummer</span>
          </dt>
          <dd>{artefactEntity.putnummer}</dd>
          <dt>
            <span id="restauratiewenselijk">Restauratiewenselijk</span>
          </dt>
          <dd>{artefactEntity.restauratiewenselijk ? 'true' : 'false'}</dd>
          <dt>
            <span id="tekeningnummer">Tekeningnummer</span>
          </dt>
          <dd>{artefactEntity.tekeningnummer}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{artefactEntity.type}</dd>
          <dt>
            <span id="vondstnummer">Vondstnummer</span>
          </dt>
          <dd>{artefactEntity.vondstnummer}</dd>
          <dt>Zitin Doos</dt>
          <dd>{artefactEntity.zitinDoos ? artefactEntity.zitinDoos.id : ''}</dd>
          <dt>Isvansoort Artefactsoort</dt>
          <dd>{artefactEntity.isvansoortArtefactsoort ? artefactEntity.isvansoortArtefactsoort.id : ''}</dd>
          <dt>Bevat Vondst</dt>
          <dd>{artefactEntity.bevatVondst ? artefactEntity.bevatVondst.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artefact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artefact/${artefactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtefactDetail;
