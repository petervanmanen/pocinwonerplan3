import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVegetatieobject } from 'app/shared/model/vegetatieobject.model';
import { getEntity, updateEntity, createEntity, reset } from './vegetatieobject.reducer';

export const VegetatieobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vegetatieobjectEntity = useAppSelector(state => state.vegetatieobject.entity);
  const loading = useAppSelector(state => state.vegetatieobject.loading);
  const updating = useAppSelector(state => state.vegetatieobject.updating);
  const updateSuccess = useAppSelector(state => state.vegetatieobject.updateSuccess);

  const handleClose = () => {
    navigate('/vegetatieobject');
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
      ...vegetatieobjectEntity,
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
          ...vegetatieobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vegetatieobject.home.createOrEditLabel" data-cy="VegetatieobjectCreateUpdateHeading">
            Create or edit a Vegetatieobject
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
                <ValidatedField name="id" required readOnly id="vegetatieobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Afvoeren" id="vegetatieobject-afvoeren" name="afvoeren" data-cy="afvoeren" check type="checkbox" />
              <ValidatedField
                label="Bereikbaarheid"
                id="vegetatieobject-bereikbaarheid"
                name="bereikbaarheid"
                data-cy="bereikbaarheid"
                type="text"
              />
              <ValidatedField
                label="Ecologischbeheer"
                id="vegetatieobject-ecologischbeheer"
                name="ecologischbeheer"
                data-cy="ecologischbeheer"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="vegetatieobject-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="vegetatieobject-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Kweker" id="vegetatieobject-kweker" name="kweker" data-cy="kweker" type="text" />
              <ValidatedField label="Leverancier" id="vegetatieobject-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField
                label="Eobjectnummer"
                id="vegetatieobject-eobjectnummer"
                name="eobjectnummer"
                data-cy="eobjectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Soortnaam" id="vegetatieobject-soortnaam" name="soortnaam" data-cy="soortnaam" type="text" />
              <ValidatedField
                label="Typestandplaats"
                id="vegetatieobject-typestandplaats"
                name="typestandplaats"
                data-cy="typestandplaats"
                type="text"
              />
              <ValidatedField
                label="Typestandplaatsplus"
                id="vegetatieobject-typestandplaatsplus"
                name="typestandplaatsplus"
                data-cy="typestandplaatsplus"
                type="text"
              />
              <ValidatedField
                label="Vegetatieobjectbereikbaarheidplus"
                id="vegetatieobject-vegetatieobjectbereikbaarheidplus"
                name="vegetatieobjectbereikbaarheidplus"
                data-cy="vegetatieobjectbereikbaarheidplus"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vegetatieobject" replace color="info">
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

export default VegetatieobjectUpdate;
