import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locatieonroerendezaak from './locatieonroerendezaak';
import LocatieonroerendezaakDetail from './locatieonroerendezaak-detail';
import LocatieonroerendezaakUpdate from './locatieonroerendezaak-update';
import LocatieonroerendezaakDeleteDialog from './locatieonroerendezaak-delete-dialog';

const LocatieonroerendezaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locatieonroerendezaak />} />
    <Route path="new" element={<LocatieonroerendezaakUpdate />} />
    <Route path=":id">
      <Route index element={<LocatieonroerendezaakDetail />} />
      <Route path="edit" element={<LocatieonroerendezaakUpdate />} />
      <Route path="delete" element={<LocatieonroerendezaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocatieonroerendezaakRoutes;
