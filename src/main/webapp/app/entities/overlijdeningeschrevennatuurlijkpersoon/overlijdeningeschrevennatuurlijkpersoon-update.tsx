import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverlijdeningeschrevennatuurlijkpersoon } from 'app/shared/model/overlijdeningeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './overlijdeningeschrevennatuurlijkpersoon.reducer';

export const OverlijdeningeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overlijdeningeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.overlijdeningeschrevennatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.overlijdeningeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.overlijdeningeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.overlijdeningeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/overlijdeningeschrevennatuurlijkpersoon');
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
      ...overlijdeningeschrevennatuurlijkpersoonEntity,
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
          ...overlijdeningeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.overlijdeningeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="OverlijdeningeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Overlijdeningeschrevennatuurlijkpersoon
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
                  id="overlijdeningeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Buitenlandseplaatsoverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-buitenlandseplaatsoverlijden"
                name="buitenlandseplaatsoverlijden"
                data-cy="buitenlandseplaatsoverlijden"
                type="text"
              />
              <ValidatedField
                label="Buitenlandseregiooverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-buitenlandseregiooverlijden"
                name="buitenlandseregiooverlijden"
                data-cy="buitenlandseregiooverlijden"
                type="text"
              />
              <ValidatedField
                label="Datumoverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-datumoverlijden"
                name="datumoverlijden"
                data-cy="datumoverlijden"
                type="text"
              />
              <ValidatedField
                label="Gemeenteoverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-gemeenteoverlijden"
                name="gemeenteoverlijden"
                data-cy="gemeenteoverlijden"
                type="text"
              />
              <ValidatedField
                label="Landofgebiedoverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-landofgebiedoverlijden"
                name="landofgebiedoverlijden"
                data-cy="landofgebiedoverlijden"
                type="text"
              />
              <ValidatedField
                label="Omschrijvinglocatieoverlijden"
                id="overlijdeningeschrevennatuurlijkpersoon-omschrijvinglocatieoverlijden"
                name="omschrijvinglocatieoverlijden"
                data-cy="omschrijvinglocatieoverlijden"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/overlijdeningeschrevennatuurlijkpersoon"
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

export default OverlijdeningeschrevennatuurlijkpersoonUpdate;
