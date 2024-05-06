import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWaterinrichtingsobject } from 'app/shared/model/waterinrichtingsobject.model';
import { getEntity, updateEntity, createEntity, reset } from './waterinrichtingsobject.reducer';

export const WaterinrichtingsobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const waterinrichtingsobjectEntity = useAppSelector(state => state.waterinrichtingsobject.entity);
  const loading = useAppSelector(state => state.waterinrichtingsobject.loading);
  const updating = useAppSelector(state => state.waterinrichtingsobject.updating);
  const updateSuccess = useAppSelector(state => state.waterinrichtingsobject.updateSuccess);

  const handleClose = () => {
    navigate('/waterinrichtingsobject');
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
      ...waterinrichtingsobjectEntity,
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
          ...waterinrichtingsobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.waterinrichtingsobject.home.createOrEditLabel" data-cy="WaterinrichtingsobjectCreateUpdateHeading">
            Create or edit a Waterinrichtingsobject
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
                <ValidatedField name="id" required readOnly id="waterinrichtingsobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanleghoogte"
                id="waterinrichtingsobject-aanleghoogte"
                name="aanleghoogte"
                data-cy="aanleghoogte"
                type="text"
              />
              <ValidatedField label="Breedte" id="waterinrichtingsobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Jaarconserveren"
                id="waterinrichtingsobject-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="waterinrichtingsobject-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="waterinrichtingsobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="waterinrichtingsobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="waterinrichtingsobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField
                label="Leverancier"
                id="waterinrichtingsobject-leverancier"
                name="leverancier"
                data-cy="leverancier"
                type="text"
              />
              <ValidatedField label="Materiaal" id="waterinrichtingsobject-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField
                label="Oppervlakte"
                id="waterinrichtingsobject-oppervlakte"
                name="oppervlakte"
                data-cy="oppervlakte"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/waterinrichtingsobject" replace color="info">
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

export default WaterinrichtingsobjectUpdate;
