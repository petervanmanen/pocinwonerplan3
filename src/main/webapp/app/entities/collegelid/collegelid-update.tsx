import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICollegelid } from 'app/shared/model/collegelid.model';
import { getEntity, updateEntity, createEntity, reset } from './collegelid.reducer';

export const CollegelidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const collegelidEntity = useAppSelector(state => state.collegelid.entity);
  const loading = useAppSelector(state => state.collegelid.loading);
  const updating = useAppSelector(state => state.collegelid.updating);
  const updateSuccess = useAppSelector(state => state.collegelid.updateSuccess);

  const handleClose = () => {
    navigate('/collegelid');
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
      ...collegelidEntity,
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
          ...collegelidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.collegelid.home.createOrEditLabel" data-cy="CollegelidCreateUpdateHeading">
            Create or edit a Collegelid
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="collegelid-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Achternaam" id="collegelid-achternaam" name="achternaam" data-cy="achternaam" type="text" />
              <ValidatedField
                label="Datumaanstelling"
                id="collegelid-datumaanstelling"
                name="datumaanstelling"
                data-cy="datumaanstelling"
                type="date"
              />
              <ValidatedField
                label="Datumuittreding"
                id="collegelid-datumuittreding"
                name="datumuittreding"
                data-cy="datumuittreding"
                type="date"
              />
              <ValidatedField label="Fractie" id="collegelid-fractie" name="fractie" data-cy="fractie" type="text" />
              <ValidatedField label="Portefeuille" id="collegelid-portefeuille" name="portefeuille" data-cy="portefeuille" type="text" />
              <ValidatedField label="Titel" id="collegelid-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField label="Voornaam" id="collegelid-voornaam" name="voornaam" data-cy="voornaam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/collegelid" replace color="info">
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

export default CollegelidUpdate;
