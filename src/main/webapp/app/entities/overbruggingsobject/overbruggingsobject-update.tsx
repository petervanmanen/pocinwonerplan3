import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverbruggingsobject } from 'app/shared/model/overbruggingsobject.model';
import { getEntity, updateEntity, createEntity, reset } from './overbruggingsobject.reducer';

export const OverbruggingsobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overbruggingsobjectEntity = useAppSelector(state => state.overbruggingsobject.entity);
  const loading = useAppSelector(state => state.overbruggingsobject.loading);
  const updating = useAppSelector(state => state.overbruggingsobject.updating);
  const updateSuccess = useAppSelector(state => state.overbruggingsobject.updateSuccess);

  const handleClose = () => {
    navigate('/overbruggingsobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...overbruggingsobjectEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...overbruggingsobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overbruggingsobject.home.createOrEditLabel" data-cy="OverbruggingsobjectCreateUpdateHeading">
            Create or edit a Overbruggingsobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="overbruggingsobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanleghoogte"
                id="overbruggingsobject-aanleghoogte"
                name="aanleghoogte"
                data-cy="aanleghoogte"
                type="text"
              />
              <ValidatedField
                label="Antigraffitivoorziening"
                id="overbruggingsobject-antigraffitivoorziening"
                name="antigraffitivoorziening"
                data-cy="antigraffitivoorziening"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Bereikbaarheid"
                id="overbruggingsobject-bereikbaarheid"
                name="bereikbaarheid"
                data-cy="bereikbaarheid"
                type="text"
              />
              <ValidatedField label="Breedte" id="overbruggingsobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Hoogte" id="overbruggingsobject-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Installateur"
                id="overbruggingsobject-installateur"
                name="installateur"
                data-cy="installateur"
                type="text"
              />
              <ValidatedField
                label="Jaarconserveren"
                id="overbruggingsobject-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="overbruggingsobject-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Jaarrenovatie"
                id="overbruggingsobject-jaarrenovatie"
                name="jaarrenovatie"
                data-cy="jaarrenovatie"
                type="text"
              />
              <ValidatedField
                label="Jaarvervanging"
                id="overbruggingsobject-jaarvervanging"
                name="jaarvervanging"
                data-cy="jaarvervanging"
                type="text"
              />
              <ValidatedField label="Kleur" id="overbruggingsobject-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="overbruggingsobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="overbruggingsobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="overbruggingsobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField
                label="Looprichel"
                id="overbruggingsobject-looprichel"
                name="looprichel"
                data-cy="looprichel"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Minimumconditiescore"
                id="overbruggingsobject-minimumconditiescore"
                name="minimumconditiescore"
                data-cy="minimumconditiescore"
                type="text"
              />
              <ValidatedField
                label="Onderhoudsregime"
                id="overbruggingsobject-onderhoudsregime"
                name="onderhoudsregime"
                data-cy="onderhoudsregime"
                type="text"
              />
              <ValidatedField
                label="Oppervlakte"
                id="overbruggingsobject-oppervlakte"
                name="oppervlakte"
                data-cy="oppervlakte"
                type="text"
              />
              <ValidatedField
                label="Overbruggingsobjectmateriaal"
                id="overbruggingsobject-overbruggingsobjectmateriaal"
                name="overbruggingsobjectmateriaal"
                data-cy="overbruggingsobjectmateriaal"
                type="text"
              />
              <ValidatedField
                label="Overbruggingsobjectmodaliteit"
                id="overbruggingsobject-overbruggingsobjectmodaliteit"
                name="overbruggingsobjectmodaliteit"
                data-cy="overbruggingsobjectmodaliteit"
                type="text"
              />
              <ValidatedField
                label="Technischelevensduur"
                id="overbruggingsobject-technischelevensduur"
                name="technischelevensduur"
                data-cy="technischelevensduur"
                type="text"
              />
              <ValidatedField
                label="Typefundering"
                id="overbruggingsobject-typefundering"
                name="typefundering"
                data-cy="typefundering"
                type="text"
              />
              <ValidatedField
                label="Vervangingswaarde"
                id="overbruggingsobject-vervangingswaarde"
                name="vervangingswaarde"
                data-cy="vervangingswaarde"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overbruggingsobject" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OverbruggingsobjectUpdate;
