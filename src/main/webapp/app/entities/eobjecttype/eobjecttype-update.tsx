import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEobjecttype } from 'app/shared/model/eobjecttype.model';
import { getEntity, updateEntity, createEntity, reset } from './eobjecttype.reducer';

export const EobjecttypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const eobjecttypeEntity = useAppSelector(state => state.eobjecttype.entity);
  const loading = useAppSelector(state => state.eobjecttype.loading);
  const updating = useAppSelector(state => state.eobjecttype.updating);
  const updateSuccess = useAppSelector(state => state.eobjecttype.updateSuccess);

  const handleClose = () => {
    navigate('/eobjecttype');
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
    const entity = {
      ...eobjecttypeEntity,
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
          ...eobjecttypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.eobjecttype.home.createOrEditLabel" data-cy="EobjecttypeCreateUpdateHeading">
            Create or edit a Eobjecttype
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="eobjecttype-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumopname" id="eobjecttype-datumopname" name="datumopname" data-cy="datumopname" type="date" />
              <ValidatedField label="Definitie" id="eobjecttype-definitie" name="definitie" data-cy="definitie" type="text" />
              <ValidatedField label="Eaguid" id="eobjecttype-eaguid" name="eaguid" data-cy="eaguid" type="text" />
              <ValidatedField label="Herkomst" id="eobjecttype-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField
                label="Herkomstdefinitie"
                id="eobjecttype-herkomstdefinitie"
                name="herkomstdefinitie"
                data-cy="herkomstdefinitie"
                type="text"
              />
              <ValidatedField
                label="Indicatieabstract"
                id="eobjecttype-indicatieabstract"
                name="indicatieabstract"
                data-cy="indicatieabstract"
                check
                type="checkbox"
              />
              <ValidatedField label="Kwaliteit" id="eobjecttype-kwaliteit" name="kwaliteit" data-cy="kwaliteit" type="text" />
              <ValidatedField label="Naam" id="eobjecttype-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Populatie" id="eobjecttype-populatie" name="populatie" data-cy="populatie" type="text" />
              <ValidatedField label="Stereotype" id="eobjecttype-stereotype" name="stereotype" data-cy="stereotype" type="text" />
              <ValidatedField label="Toelichting" id="eobjecttype-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Uniekeaanduiding"
                id="eobjecttype-uniekeaanduiding"
                name="uniekeaanduiding"
                data-cy="uniekeaanduiding"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/eobjecttype" replace color="info">
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

export default EobjecttypeUpdate;
