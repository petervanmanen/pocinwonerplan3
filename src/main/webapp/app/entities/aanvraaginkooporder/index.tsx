import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraaginkooporder from './aanvraaginkooporder';
import AanvraaginkooporderDetail from './aanvraaginkooporder-detail';
import AanvraaginkooporderUpdate from './aanvraaginkooporder-update';
import AanvraaginkooporderDeleteDialog from './aanvraaginkooporder-delete-dialog';

const AanvraaginkooporderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraaginkooporder />} />
    <Route path="new" element={<AanvraaginkooporderUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraaginkooporderDetail />} />
      <Route path="edit" element={<AanvraaginkooporderUpdate />} />
      <Route path="delete" element={<AanvraaginkooporderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraaginkooporderRoutes;
