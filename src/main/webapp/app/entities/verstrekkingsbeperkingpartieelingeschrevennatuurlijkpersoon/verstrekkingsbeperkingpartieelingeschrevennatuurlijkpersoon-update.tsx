import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon } from 'app/shared/model/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.reducer';

export const VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity = useAppSelector(
    state => state.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.entity,
  );
  const loading = useAppSelector(state => state.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon');
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
      ...verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity,
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
          ...verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Gemeenteverordening"
                id="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-gemeenteverordening"
                name="gemeenteverordening"
                data-cy="gemeenteverordening"
                type="text"
              />
              <ValidatedField
                label="Omschrijvingderde"
                id="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-omschrijvingderde"
                name="omschrijvingderde"
                data-cy="omschrijvingderde"
                type="text"
              />
              <ValidatedField
                label="Partij"
                id="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon-partij"
                name="partij"
                data-cy="partij"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon"
                replace
                color="info"
              >
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

export default VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonUpdate;
