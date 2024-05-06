import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeboorteingeschrevennatuurlijkpersoon } from 'app/shared/model/geboorteingeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './geboorteingeschrevennatuurlijkpersoon.reducer';

export const GeboorteingeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const geboorteingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.geboorteingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/geboorteingeschrevennatuurlijkpersoon');
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
      ...geboorteingeschrevennatuurlijkpersoonEntity,
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
          ...geboorteingeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.geboorteingeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="GeboorteingeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Geboorteingeschrevennatuurlijkpersoon
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
                  id="geboorteingeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Buitenlandseplaatsgeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-buitenlandseplaatsgeboorte"
                name="buitenlandseplaatsgeboorte"
                data-cy="buitenlandseplaatsgeboorte"
                type="text"
              />
              <ValidatedField
                label="Buitenlandseregiogeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-buitenlandseregiogeboorte"
                name="buitenlandseregiogeboorte"
                data-cy="buitenlandseregiogeboorte"
                type="text"
              />
              <ValidatedField
                label="Datumgeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-datumgeboorte"
                name="datumgeboorte"
                data-cy="datumgeboorte"
                type="text"
              />
              <ValidatedField
                label="Gemeentegeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-gemeentegeboorte"
                name="gemeentegeboorte"
                data-cy="gemeentegeboorte"
                type="text"
              />
              <ValidatedField
                label="Landofgebiedgeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-landofgebiedgeboorte"
                name="landofgebiedgeboorte"
                data-cy="landofgebiedgeboorte"
                type="text"
              />
              <ValidatedField
                label="Omschrijvinglocatiegeboorte"
                id="geboorteingeschrevennatuurlijkpersoon-omschrijvinglocatiegeboorte"
                name="omschrijvinglocatiegeboorte"
                data-cy="omschrijvinglocatiegeboorte"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/geboorteingeschrevennatuurlijkpersoon"
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

export default GeboorteingeschrevennatuurlijkpersoonUpdate;
