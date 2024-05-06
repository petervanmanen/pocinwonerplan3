import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWeginrichtingsobject } from 'app/shared/model/weginrichtingsobject.model';
import { getEntity, updateEntity, createEntity, reset } from './weginrichtingsobject.reducer';

export const WeginrichtingsobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const weginrichtingsobjectEntity = useAppSelector(state => state.weginrichtingsobject.entity);
  const loading = useAppSelector(state => state.weginrichtingsobject.loading);
  const updating = useAppSelector(state => state.weginrichtingsobject.updating);
  const updateSuccess = useAppSelector(state => state.weginrichtingsobject.updateSuccess);

  const handleClose = () => {
    navigate('/weginrichtingsobject');
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
      ...weginrichtingsobjectEntity,
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
          ...weginrichtingsobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.weginrichtingsobject.home.createOrEditLabel" data-cy="WeginrichtingsobjectCreateUpdateHeading">
            Create or edit a Weginrichtingsobject
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
                <ValidatedField name="id" required readOnly id="weginrichtingsobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanleghoogte"
                id="weginrichtingsobject-aanleghoogte"
                name="aanleghoogte"
                data-cy="aanleghoogte"
                type="text"
              />
              <ValidatedField label="Breedte" id="weginrichtingsobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Hoogte" id="weginrichtingsobject-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaarconserveren"
                id="weginrichtingsobject-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="weginrichtingsobject-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="weginrichtingsobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="weginrichtingsobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="weginrichtingsobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField
                label="Leverancier"
                id="weginrichtingsobject-leverancier"
                name="leverancier"
                data-cy="leverancier"
                type="text"
              />
              <ValidatedField label="Materiaal" id="weginrichtingsobject-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField
                label="Oppervlakte"
                id="weginrichtingsobject-oppervlakte"
                name="oppervlakte"
                data-cy="oppervlakte"
                type="text"
              />
              <ValidatedField
                label="Weginrichtingsobjectwegfunctie"
                id="weginrichtingsobject-weginrichtingsobjectwegfunctie"
                name="weginrichtingsobjectwegfunctie"
                data-cy="weginrichtingsobjectwegfunctie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/weginrichtingsobject" replace color="info">
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

export default WeginrichtingsobjectUpdate;
