import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INietnatuurlijkpersoon } from 'app/shared/model/nietnatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './nietnatuurlijkpersoon.reducer';

export const NietnatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nietnatuurlijkpersoonEntity = useAppSelector(state => state.nietnatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.nietnatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.nietnatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.nietnatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/nietnatuurlijkpersoon');
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
      ...nietnatuurlijkpersoonEntity,
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
          ...nietnatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.nietnatuurlijkpersoon.home.createOrEditLabel" data-cy="NietnatuurlijkpersoonCreateUpdateHeading">
            Create or edit a Nietnatuurlijkpersoon
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
                <ValidatedField name="id" required readOnly id="nietnatuurlijkpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumaanvang"
                id="nietnatuurlijkpersoon-datumaanvang"
                name="datumaanvang"
                data-cy="datumaanvang"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="nietnatuurlijkpersoon-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumuitschrijving"
                id="nietnatuurlijkpersoon-datumuitschrijving"
                name="datumuitschrijving"
                data-cy="datumuitschrijving"
                type="date"
              />
              <ValidatedField
                label="Datumvoortzetting"
                id="nietnatuurlijkpersoon-datumvoortzetting"
                name="datumvoortzetting"
                data-cy="datumvoortzetting"
                type="date"
              />
              <ValidatedField
                label="Faxnummer"
                id="nietnatuurlijkpersoon-faxnummer"
                name="faxnummer"
                data-cy="faxnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Ingeschreven"
                id="nietnatuurlijkpersoon-ingeschreven"
                name="ingeschreven"
                data-cy="ingeschreven"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Inoprichting"
                id="nietnatuurlijkpersoon-inoprichting"
                name="inoprichting"
                data-cy="inoprichting"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Kvknummer"
                id="nietnatuurlijkpersoon-kvknummer"
                name="kvknummer"
                data-cy="kvknummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Nnpid" id="nietnatuurlijkpersoon-nnpid" name="nnpid" data-cy="nnpid" type="text" />
              <ValidatedField label="Rechtsvorm" id="nietnatuurlijkpersoon-rechtsvorm" name="rechtsvorm" data-cy="rechtsvorm" type="text" />
              <ValidatedField
                label="Rsinnummer"
                id="nietnatuurlijkpersoon-rsinnummer"
                name="rsinnummer"
                data-cy="rsinnummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Statutairenaam"
                id="nietnatuurlijkpersoon-statutairenaam"
                name="statutairenaam"
                data-cy="statutairenaam"
                type="text"
              />
              <ValidatedField
                label="Statutairezetel"
                id="nietnatuurlijkpersoon-statutairezetel"
                name="statutairezetel"
                data-cy="statutairezetel"
                type="text"
              />
              <ValidatedField label="Websiteurl" id="nietnatuurlijkpersoon-websiteurl" name="websiteurl" data-cy="websiteurl" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nietnatuurlijkpersoon" replace color="info">
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

export default NietnatuurlijkpersoonUpdate;
