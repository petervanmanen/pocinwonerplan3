import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMelding } from 'app/shared/model/melding.model';
import { getEntities as getMeldings } from 'app/entities/melding/melding.reducer';
import { IBeheerobject } from 'app/shared/model/beheerobject.model';
import { getEntity, updateEntity, createEntity, reset } from './beheerobject.reducer';

export const BeheerobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const meldings = useAppSelector(state => state.melding.entities);
  const beheerobjectEntity = useAppSelector(state => state.beheerobject.entity);
  const loading = useAppSelector(state => state.beheerobject.loading);
  const updating = useAppSelector(state => state.beheerobject.updating);
  const updateSuccess = useAppSelector(state => state.beheerobject.updateSuccess);

  const handleClose = () => {
    navigate('/beheerobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMeldings({}));
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
      ...beheerobjectEntity,
      ...values,
      betreftMeldings: mapIdList(values.betreftMeldings),
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
          ...beheerobjectEntity,
          betreftMeldings: beheerobjectEntity?.betreftMeldings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beheerobject.home.createOrEditLabel" data-cy="BeheerobjectCreateUpdateHeading">
            Create or edit a Beheerobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="beheerobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="beheerobject-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField
                label="Begingarantieperiode"
                id="beheerobject-begingarantieperiode"
                name="begingarantieperiode"
                data-cy="begingarantieperiode"
                type="date"
              />
              <ValidatedField label="Beheergebied" id="beheerobject-beheergebied" name="beheergebied" data-cy="beheergebied" type="text" />
              <ValidatedField
                label="Beheerobjectbeheervak"
                id="beheerobject-beheerobjectbeheervak"
                name="beheerobjectbeheervak"
                data-cy="beheerobjectbeheervak"
                type="text"
              />
              <ValidatedField
                label="Beheerobjectgebruiksfunctie"
                id="beheerobject-beheerobjectgebruiksfunctie"
                name="beheerobjectgebruiksfunctie"
                data-cy="beheerobjectgebruiksfunctie"
                type="text"
              />
              <ValidatedField
                label="Beheerobjectmemo"
                id="beheerobject-beheerobjectmemo"
                name="beheerobjectmemo"
                data-cy="beheerobjectmemo"
                type="text"
              />
              <ValidatedField
                label="Beschermdefloraenfauna"
                id="beheerobject-beschermdefloraenfauna"
                name="beschermdefloraenfauna"
                data-cy="beschermdefloraenfauna"
                check
                type="checkbox"
              />
              <ValidatedField label="Buurt" id="beheerobject-buurt" name="buurt" data-cy="buurt" type="text" />
              <ValidatedField label="Conversieid" id="beheerobject-conversieid" name="conversieid" data-cy="conversieid" type="text" />
              <ValidatedField label="Datummutatie" id="beheerobject-datummutatie" name="datummutatie" data-cy="datummutatie" type="date" />
              <ValidatedField
                label="Datumoplevering"
                id="beheerobject-datumoplevering"
                name="datumoplevering"
                data-cy="datumoplevering"
                type="date"
              />
              <ValidatedField
                label="Datumpublicatielv"
                id="beheerobject-datumpublicatielv"
                name="datumpublicatielv"
                data-cy="datumpublicatielv"
                type="date"
              />
              <ValidatedField
                label="Datumverwijdering"
                id="beheerobject-datumverwijdering"
                name="datumverwijdering"
                data-cy="datumverwijdering"
                type="date"
              />
              <ValidatedField
                label="Eindegarantieperiode"
                id="beheerobject-eindegarantieperiode"
                name="eindegarantieperiode"
                data-cy="eindegarantieperiode"
                type="date"
              />
              <ValidatedField label="Gebiedstype" id="beheerobject-gebiedstype" name="gebiedstype" data-cy="gebiedstype" type="text" />
              <ValidatedField label="Gemeente" id="beheerobject-gemeente" name="gemeente" data-cy="gemeente" type="text" />
              <ValidatedField label="Geometrie" id="beheerobject-geometrie" name="geometrie" data-cy="geometrie" type="text" />
              <ValidatedField
                label="Gewijzigddoor"
                id="beheerobject-gewijzigddoor"
                name="gewijzigddoor"
                data-cy="gewijzigddoor"
                type="text"
              />
              <ValidatedField label="Grondsoort" id="beheerobject-grondsoort" name="grondsoort" data-cy="grondsoort" type="text" />
              <ValidatedField
                label="Grondsoortplus"
                id="beheerobject-grondsoortplus"
                name="grondsoortplus"
                data-cy="grondsoortplus"
                type="text"
              />
              <ValidatedField
                label="Identificatieimbor"
                id="beheerobject-identificatieimbor"
                name="identificatieimbor"
                data-cy="identificatieimbor"
                type="text"
              />
              <ValidatedField
                label="Identificatieimgeo"
                id="beheerobject-identificatieimgeo"
                name="identificatieimgeo"
                data-cy="identificatieimgeo"
                type="text"
              />
              <ValidatedField
                label="Jaarvanaanleg"
                id="beheerobject-jaarvanaanleg"
                name="jaarvanaanleg"
                data-cy="jaarvanaanleg"
                type="text"
              />
              <ValidatedField
                label="Eobjectbegintijd"
                id="beheerobject-eobjectbegintijd"
                name="eobjectbegintijd"
                data-cy="eobjectbegintijd"
                type="text"
              />
              <ValidatedField
                label="Eobjecteindtijd"
                id="beheerobject-eobjecteindtijd"
                name="eobjecteindtijd"
                data-cy="eobjecteindtijd"
                type="text"
              />
              <ValidatedField
                label="Onderhoudsplichtige"
                id="beheerobject-onderhoudsplichtige"
                name="onderhoudsplichtige"
                data-cy="onderhoudsplichtige"
                type="text"
              />
              <ValidatedField
                label="Openbareruimte"
                id="beheerobject-openbareruimte"
                name="openbareruimte"
                data-cy="openbareruimte"
                type="text"
              />
              <ValidatedField label="Postcode" id="beheerobject-postcode" name="postcode" data-cy="postcode" type="text" />
              <ValidatedField
                label="Relatievehoogteligging"
                id="beheerobject-relatievehoogteligging"
                name="relatievehoogteligging"
                data-cy="relatievehoogteligging"
                type="text"
              />
              <ValidatedField label="Stadsdeel" id="beheerobject-stadsdeel" name="stadsdeel" data-cy="stadsdeel" type="text" />
              <ValidatedField label="Status" id="beheerobject-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Theoretischeindejaar"
                id="beheerobject-theoretischeindejaar"
                name="theoretischeindejaar"
                data-cy="theoretischeindejaar"
                type="text"
              />
              <ValidatedField
                label="Tijdstipregistratie"
                id="beheerobject-tijdstipregistratie"
                name="tijdstipregistratie"
                data-cy="tijdstipregistratie"
                type="text"
              />
              <ValidatedField
                label="Typebeheerder"
                id="beheerobject-typebeheerder"
                name="typebeheerder"
                data-cy="typebeheerder"
                type="text"
              />
              <ValidatedField
                label="Typebeheerderplus"
                id="beheerobject-typebeheerderplus"
                name="typebeheerderplus"
                data-cy="typebeheerderplus"
                type="text"
              />
              <ValidatedField label="Typeeigenaar" id="beheerobject-typeeigenaar" name="typeeigenaar" data-cy="typeeigenaar" type="text" />
              <ValidatedField
                label="Typeeigenaarplus"
                id="beheerobject-typeeigenaarplus"
                name="typeeigenaarplus"
                data-cy="typeeigenaarplus"
                type="text"
              />
              <ValidatedField label="Typeligging" id="beheerobject-typeligging" name="typeligging" data-cy="typeligging" type="text" />
              <ValidatedField label="Waterschap" id="beheerobject-waterschap" name="waterschap" data-cy="waterschap" type="text" />
              <ValidatedField label="Wijk" id="beheerobject-wijk" name="wijk" data-cy="wijk" type="text" />
              <ValidatedField label="Woonplaats" id="beheerobject-woonplaats" name="woonplaats" data-cy="woonplaats" type="text" />
              <ValidatedField
                label="Zettingsgevoeligheid"
                id="beheerobject-zettingsgevoeligheid"
                name="zettingsgevoeligheid"
                data-cy="zettingsgevoeligheid"
                type="text"
              />
              <ValidatedField
                label="Zettingsgevoeligheidplus"
                id="beheerobject-zettingsgevoeligheidplus"
                name="zettingsgevoeligheidplus"
                data-cy="zettingsgevoeligheidplus"
                type="text"
              />
              <ValidatedField
                label="Betreft Melding"
                id="beheerobject-betreftMelding"
                data-cy="betreftMelding"
                type="select"
                multiple
                name="betreftMeldings"
              >
                <option value="" key="0" />
                {meldings
                  ? meldings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beheerobject" replace color="info">
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

export default BeheerobjectUpdate;
