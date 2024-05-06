import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INaamnatuurlijkpersoon } from 'app/shared/model/naamnatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './naamnatuurlijkpersoon.reducer';

export const NaamnatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const naamnatuurlijkpersoonEntity = useAppSelector(state => state.naamnatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.naamnatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.naamnatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.naamnatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/naamnatuurlijkpersoon');
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
      ...naamnatuurlijkpersoonEntity,
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
          ...naamnatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.naamnatuurlijkpersoon.home.createOrEditLabel" data-cy="NaamnatuurlijkpersoonCreateUpdateHeading">
            Create or edit a Naamnatuurlijkpersoon
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
                <ValidatedField name="id" required readOnly id="naamnatuurlijkpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adellijketitelofpredikaat"
                id="naamnatuurlijkpersoon-adellijketitelofpredikaat"
                name="adellijketitelofpredikaat"
                data-cy="adellijketitelofpredikaat"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Geslachtsnaam"
                id="naamnatuurlijkpersoon-geslachtsnaam"
                name="geslachtsnaam"
                data-cy="geslachtsnaam"
                type="text"
              />
              <ValidatedField label="Voornamen" id="naamnatuurlijkpersoon-voornamen" name="voornamen" data-cy="voornamen" type="text" />
              <ValidatedField
                label="Voorvoegselgeslachtsnaam"
                id="naamnatuurlijkpersoon-voorvoegselgeslachtsnaam"
                name="voorvoegselgeslachtsnaam"
                data-cy="voorvoegselgeslachtsnaam"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/naamnatuurlijkpersoon" replace color="info">
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

export default NaamnatuurlijkpersoonUpdate;
