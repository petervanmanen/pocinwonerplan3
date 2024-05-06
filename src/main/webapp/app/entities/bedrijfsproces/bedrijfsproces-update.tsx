import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IBedrijfsproces } from 'app/shared/model/bedrijfsproces.model';
import { getEntity, updateEntity, createEntity, reset } from './bedrijfsproces.reducer';

export const BedrijfsprocesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaks = useAppSelector(state => state.zaak.entities);
  const bedrijfsprocesEntity = useAppSelector(state => state.bedrijfsproces.entity);
  const loading = useAppSelector(state => state.bedrijfsproces.loading);
  const updating = useAppSelector(state => state.bedrijfsproces.updating);
  const updateSuccess = useAppSelector(state => state.bedrijfsproces.updateSuccess);

  const handleClose = () => {
    navigate('/bedrijfsproces');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaks({}));
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
      ...bedrijfsprocesEntity,
      ...values,
      uitgevoerdbinnenZaaks: mapIdList(values.uitgevoerdbinnenZaaks),
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
          ...bedrijfsprocesEntity,
          uitgevoerdbinnenZaaks: bedrijfsprocesEntity?.uitgevoerdbinnenZaaks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bedrijfsproces.home.createOrEditLabel" data-cy="BedrijfsprocesCreateUpdateHeading">
            Create or edit a Bedrijfsproces
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
                <ValidatedField name="id" required readOnly id="bedrijfsproces-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Afgerond" id="bedrijfsproces-afgerond" name="afgerond" data-cy="afgerond" type="text" />
              <ValidatedField label="Datumeind" id="bedrijfsproces-datumeind" name="datumeind" data-cy="datumeind" type="date" />
              <ValidatedField label="Datumstart" id="bedrijfsproces-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Naam" id="bedrijfsproces-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="bedrijfsproces-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Uitgevoerdbinnen Zaak"
                id="bedrijfsproces-uitgevoerdbinnenZaak"
                data-cy="uitgevoerdbinnenZaak"
                type="select"
                multiple
                name="uitgevoerdbinnenZaaks"
              >
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bedrijfsproces" replace color="info">
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

export default BedrijfsprocesUpdate;
